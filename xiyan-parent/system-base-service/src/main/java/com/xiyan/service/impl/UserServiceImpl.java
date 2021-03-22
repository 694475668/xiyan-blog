package com.xiyan.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiyan.domain.UserDO;
import com.xiyan.dto.*;
import com.xiyan.enumeration.ErrorCodeEnum;
import com.xiyan.mapper.UserMapper;
import com.xiyan.service.UserService;
import com.xiyan.util.BcryptPasswordUtil;
import com.xiyan.util.JWTUtil;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.UserByIdVO;
import com.xiyan.vo.UserTokenVO;
import com.xiyan.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author: bright
 * @date:Created in 2020/11/8 14:46
 * @describe :
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private JavaMailSender javaMailSender;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public BaseVO sendOutEmail(EmailDTO emailDTO) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("xiyanyuanma@163.com");
        helper.setTo(emailDTO.getUsername());
        helper.setSubject("夕颜源码");

        String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        //验证码保存在redis中
        redisTemplate.opsForValue().set(emailDTO.getUsername(), checkCode, 3, TimeUnit.MINUTES);
        String str = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "\n" +
                "	<head>\n" +
                "		<meta charset=\"UTF-8\">\n" +
                "		<title>Title</title>\n" +
                "	</head>\n" +
                "\n" +
                "	<body>\n" +
                "		<div style=\"background-color:#ECECEC; padding: 35px;\">\n" +
                "			<table cellpadding=\"0\" align=\"center\" style=\"width: 600px; margin: 0px auto; text-align: left; position: relative; border-top-left-radius: 5px; border-top-right-radius: 5px; border-bottom-right-radius: 5px; border-bottom-left-radius: 5px; font-size: 14px; font-family:微软雅黑, 黑体; line-height: 1.5; box-shadow: rgb(153, 153, 153) 0px 0px 5px; border-collapse: collapse; background-position: initial initial; background-repeat: initial initial;background:#fff;\">\n" +
                "				<tbody>\n" +
                "					<tr>\n" +
                "						<th valign=\"middle\" style=\"height: 25px; line-height: 25px; padding: 15px 35px; border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: #42a3d3; background-color: #49bcff; border-top-left-radius: 5px; border-top-right-radius: 5px; border-bottom-right-radius: 0px; border-bottom-left-radius: 0px;\">\n" +
                "							<font face=\"微软雅黑\" size=\"5\" style=\"color: rgb(255, 255, 255); \">邮箱验证! （夕颜）</font>\n" +
                "						</th>\n" +
                "					</tr>\n" +
                "					<tr>\n" +
                "						<td>\n" +
                "							<div style=\"padding:25px 35px 40px; background-color:#fff;\">\n" +
                "								<h5 style=\"margin: 5px 0px; \">\n" +
                "                        <font color=\"#333333\" style=\"line-height: 20px; \">\n" +
                "                            <font style=\"line-height: 22px; \" size=\"4\">\n" +
                "                            欢迎您注册夕颜源码，您的账号激活验证码是：<span style=\"color:red\">" + checkCode + "</span>&nbsp;&nbsp;三分钟之内有效" + "<br /><br /><br />\n" +
                "                            </font>\n" +
                "                        </font>\n" +
                "                    </h5> 当您在使用本网站时，遵守当地法律法规。\n" +
                "								<br> 如果您有什么疑问可以联系管理员，Email: 694475668@qq.com</p>\n" +
                "								<p align=\"right\">夕颜官方团队</p>\n" +
                "								<div style=\"width:700px;margin:0 auto;\">\n" +
                "									<div style=\"padding:10px 10px 0;border-top:1px solid #ccc;color:#747474;margin-bottom:20px;line-height:1.3em;font-size:12px;\">\n" +
                "										<p>此为系统邮件，请勿回复<br> 请保管好您的邮箱，避免账号被他人盗用\n" +
                "										</p>\n" +
                "										<p>©湘ICP备20008715号</p>\n" +
                "									</div>\n" +
                "								</div>\n" +
                "							</div>\n" +
                "						</td>\n" +
                "					</tr>\n" +
                "				</tbody>\n" +
                "			</table>\n" +
                "		</div>\n" +
                "	</body>\n" +
                "\n" +
                "</html>";
        helper.setText(str, true);
        //异步发送不堵塞
        new Thread(new Runnable() {
            @Override
            public void run() {
                javaMailSender.send(message);
            }
        }) {
        }.start();
        return new BaseVO();
    }

    @Override
    public BaseVO register(UserDTO userDTO) {
        //先验证邮箱是否已经被注册
        UserDO user = userMapper.selectOne(new QueryWrapper<UserDO>().eq("username", userDTO.getUsername()));
        if (null != user) {
            return new BaseVO(false, ErrorCodeEnum.E0851.getKey(), ErrorCodeEnum.E0851.getValue());
        }
        String code = redisTemplate.opsForValue().get(userDTO.getUsername());
        if (StringUtils.isBlank(code)) {
            return new BaseVO(false, ErrorCodeEnum.E0852.getKey(), ErrorCodeEnum.E0852.getValue());
        }
        if (!code.equals(userDTO.getCode())) {
            return new BaseVO(false, ErrorCodeEnum.E0853.getKey(), ErrorCodeEnum.E0853.getValue());
        }
        UserDO userDO = new UserDO();
        userDO.setName("用户" + (int) (Math.random() * 1000000000));
        userDO.setCreateTime(new Date());
        userDO.setUsername(userDTO.getUsername());
        userDO.setPassword(BcryptPasswordUtil.createBCryptPassword(userDTO.getPassword()));
        userDO.setPhoto("http://qiniu-picture.xiyanit.cn/%E6%B3%A8%E5%86%8C%E7%94%A8%E6%88%B7%E6%97%B6%E4%BD%BF%E7%94%A8%E7%9A%84%E5%A4%B4%E5%83%8F.jpg");
        userDO.setPoint(5);
        userDO.setState("0");
        userDO.setIsMember("0");
        userMapper.insert(userDO);
        UserTokenVO userTokenVO = new UserTokenVO();
        UserVO userVO = new UserVO();
        //免登录
        user = userMapper.selectOne(new QueryWrapper<UserDO>().eq("username", userDTO.getUsername()));
        userTokenVO.setToken(JWTUtil.createToken(String.valueOf(user.getId()), user.getUsername()));
        BeanUtils.copyProperties(user, userVO);
        userTokenVO.setUserVO(userVO);
        //删除验证码
        redisTemplate.delete(userDTO.getUsername());
        return userTokenVO;
    }

    @Override
    public BaseVO bind(AccountLoginDTO accountLoginDTO, Integer id) {
        UserDO getUser = userMapper.selectOne(new QueryWrapper<UserDO>().eq("username", accountLoginDTO.getUsername()));
        if (null != getUser) {
            return new BaseVO(false, ErrorCodeEnum.E0859.getKey(), ErrorCodeEnum.E0859.getValue());
        }
        UserDO user = new UserDO();
        BeanUtils.copyProperties(accountLoginDTO, user);
        user.setId(id);
        user.setPassword(BcryptPasswordUtil.createBCryptPassword(accountLoginDTO.getPassword()));
        userMapper.updateById(user);
        //绑定成功返回用户信息
        UserDO userDO = userMapper.selectOne(new QueryWrapper<UserDO>().eq("username", accountLoginDTO.getUsername()));
        UserTokenVO userTokenVO = new UserTokenVO();
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDO, userVO);
        userTokenVO.setUserVO(userVO);
        return userTokenVO;
    }

    @Override
    public UserByIdVO getUser(GetUserDTO getUserDTO) {
        UserByIdVO userByIdVO = new UserByIdVO();
        UserDO userDO = null;
        if (null != getUserDTO.getId()) {
            userDO = userMapper.selectById(getUserDTO.getId());
        } else if (StringUtils.isNotBlank(getUserDTO.getUsername())) {
            userDO = userMapper.selectOne(new QueryWrapper<UserDO>().eq("username", getUserDTO.getUsername()));
        }
        BeanUtils.copyProperties(userDO, userByIdVO);
        return userByIdVO;
    }

    @Override
    public BaseVO update(UserUpdateDTO userUpdateDTO) {
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userUpdateDTO, userDO);
        userMapper.updateById(userDO);
        return new BaseVO();
    }

    @Override
    public BaseVO retrievePassword(UserDTO userDTO) {
        //先验证邮箱是否存在
        UserDO user = userMapper.selectOne(new QueryWrapper<UserDO>().eq("username", userDTO.getUsername()));
        if (null == user) {
            return new BaseVO(false, ErrorCodeEnum.E0855.getKey(), ErrorCodeEnum.E0855.getValue());
        }
        String code = redisTemplate.opsForValue().get(userDTO.getUsername());
        if (StringUtils.isBlank(code)) {
            return new BaseVO(false, ErrorCodeEnum.E0852.getKey(), ErrorCodeEnum.E0852.getValue());
        }
        if (!code.equals(userDTO.getCode())) {
            return new BaseVO(false, ErrorCodeEnum.E0853.getKey(), ErrorCodeEnum.E0853.getValue());
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userDTO, userDO);
        userDO.setPassword(BcryptPasswordUtil.createBCryptPassword(userDTO.getPassword()));
        userMapper.update(userDO, new QueryWrapper<UserDO>().eq("username", userDO.getUsername()));
        //删除验证码
        redisTemplate.delete(userDTO.getUsername());
        return new BaseVO();
    }
}