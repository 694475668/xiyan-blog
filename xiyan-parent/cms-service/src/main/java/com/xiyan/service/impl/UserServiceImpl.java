package com.xiyan.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiyan.domain.UserDO;
import com.xiyan.dto.AccountLoginDTO;
import com.xiyan.dto.EmailDTO;
import com.xiyan.dto.UserDTO;
import com.xiyan.enumeration.ErrorCodeEnum;
import com.xiyan.mapper.UserMapper;
import com.xiyan.service.UserService;
import com.xiyan.util.JWTUtil;
import com.xiyan.util.QQHttpClient;
import com.xiyan.util.WeiBoHttpUtil;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.QQRequestVO;
import com.xiyan.vo.UserTokenVO;
import com.xiyan.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
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

    @Value("${qq.back}")
    private String qqBackUrl;

    @Value("${qq.app_id}")
    private String appId;

    @Value("${qq.app_key}")
    private String appKey;

    @Value("${weibo.client_id}")
    private String clientId;

    @Value("${weibo.client_secret}")
    private String clientSecret;

    @Value("${weibo.back}")
    private String weiboBackUrl;

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
            return new BaseVO(false, ErrorCodeEnum.E0701.getKey(), ErrorCodeEnum.E0701.getValue());
        }
        String code = redisTemplate.opsForValue().get(userDTO.getUsername());
        if (StringUtils.isBlank(code)) {
            return new BaseVO(false, ErrorCodeEnum.E0702.getKey(), ErrorCodeEnum.E0702.getValue());
        }
        if (!code.equals(userDTO.getCode())) {
            return new BaseVO(false, ErrorCodeEnum.E0703.getKey(), ErrorCodeEnum.E0703.getValue());
        }
        UserDO userDO = new UserDO();
        userDO.setName("用户" + (int) (Math.random() * 1000000000));
        userDO.setCreateTime(new Date());
        userDO.setUsername(userDTO.getUsername());
        userDO.setPassword(DigestUtils.md5DigestAsHex(userDTO.getPassword().getBytes()));
        userDO.setPhoto("http://qiniu-picture.xiyanyuanma.com/%E6%B3%A8%E5%86%8C%E7%94%A8%E6%88%B7%E6%97%B6%E4%BD%BF%E7%94%A8%E7%9A%84%E5%A4%B4%E5%83%8F.jpg");
        userDO.setPoint(5);
        userDO.setState("0");
        userDO.setIsMember("0");
        userMapper.insert(userDO);
        UserTokenVO userTokenVO = new UserTokenVO();
        UserVO userVO = new UserVO();
        user = userMapper.selectOne(new QueryWrapper<UserDO>().eq("username", userDTO.getUsername()).eq("password", DigestUtils.md5DigestAsHex(userDTO.getPassword().getBytes())));
        if (null != user) {
            userTokenVO.setToken(JWTUtil.createToken(String.valueOf(user.getId()), user.getUsername()));
            BeanUtils.copyProperties(user, userVO);
            userTokenVO.setUserVO(userVO);
        }
        //删除验证码
        redisTemplate.delete(userDTO.getUsername());
        return userTokenVO;
    }

    @Override
    public BaseVO login(AccountLoginDTO accountLoginDTO) {
        UserTokenVO userTokenVO = new UserTokenVO();
        UserVO userVO = new UserVO();
        UserDO user = userMapper.selectOne(new QueryWrapper<UserDO>().eq("username", accountLoginDTO.getUsername()).eq("password", DigestUtils.md5DigestAsHex(accountLoginDTO.getPassword().getBytes())));
        if (null != user) {
            userTokenVO.setToken(JWTUtil.createToken(String.valueOf(user.getId()), user.getUsername()));
            BeanUtils.copyProperties(user, userVO);
            userTokenVO.setUserVO(userVO);
            return userTokenVO;
        }
        return new BaseVO(false, ErrorCodeEnum.E0704.getKey(), ErrorCodeEnum.E0704.getValue());
    }

    @Override
    public BaseVO bind(AccountLoginDTO accountLoginDTO, Integer id) {
        UserDO user = new UserDO();
        BeanUtils.copyProperties(accountLoginDTO, user);
        user.setId(id);
        userMapper.updateById(user);
        return new BaseVO();
    }

    @Override
    public BaseVO retrievePassword(UserDTO userDTO) {
        //先验证邮箱是否存在
        UserDO user = userMapper.selectOne(new QueryWrapper<UserDO>().eq("username", userDTO.getUsername()));
        if (null == user) {
            return new BaseVO(false, ErrorCodeEnum.E0705.getKey(), ErrorCodeEnum.E0705.getValue());
        }
        String code = redisTemplate.opsForValue().get(userDTO.getUsername());
        if (StringUtils.isBlank(code)) {
            return new BaseVO(false, ErrorCodeEnum.E0702.getKey(), ErrorCodeEnum.E0702.getValue());
        }
        if (!code.equals(userDTO.getCode())) {
            return new BaseVO(false, ErrorCodeEnum.E0703.getKey(), ErrorCodeEnum.E0703.getValue());
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userDTO, userDO);
        userMapper.update(userDO, new QueryWrapper<UserDO>().eq("username", userDO.getUsername()));
        //删除验证码
        redisTemplate.delete(userDTO.getUsername());
        return new BaseVO();
    }

    @Override
    public QQRequestVO login() throws UnsupportedEncodingException {
        QQRequestVO qqRequest = new QQRequestVO();
        //用于第三方应用防止CSRF攻击
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        redisTemplate.opsForValue().set("yan-state-" + uuid, uuid);
        //Step1：获取Authorization Code
        String url = "https://graph.qq.com/oauth2.0/authorize?response_type=code" +
                "&client_id=" + appId +
                "&redirect_uri=" + URLEncoder.encode(qqBackUrl, "UTF-8") +
                "&state=" + uuid;
        qqRequest.setUrl(url);
        return qqRequest;
    }

    @Override
    public BaseVO QQBack(String code, String state) throws IOException {
        String yanState = redisTemplate.opsForValue().get("yan-state-" + state);
        //第三方应用防止CSRF攻击校验
        if (!yanState.equals(state)) {
            return new BaseVO(false, ErrorCodeEnum.E0706.getKey(), ErrorCodeEnum.E0706.getValue());
        }
        //Step2：通过Authorization Code获取Access Token
        String url = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code" +
                "&client_id=" + appId +
                "&client_secret=" + appKey +
                "&code=" + code +
                "&redirect_uri=" + qqBackUrl;
        //Step3: 获取回调后的 openid 值
        String accessToken = QQHttpClient.getAccessToken(url);
        url = "https://graph.qq.com/oauth2.0/me?access_token=" + accessToken;
        String openId = QQHttpClient.getOpenID(url);
        UserDO userDO = new UserDO();
        userDO.setState("0");
        userDO.setCreateTime(new Date());
        userDO.setPoint(5);
        userDO.setIsMember("0");
        userDO.setOpenId(openId);
        //网站被CSRF攻击了或者用户取消了授权
        if ("".equals(accessToken) || null == accessToken) {
            log.info("用户取消了授权");
        } else {
            //Step4：获取QQ用户信息
            url = "https://graph.qq.com/user/get_user_info?access_token=" + accessToken +
                    "&oauth_consumer_key=" + appId +
                    "&openid=" + openId;
            JSONObject jsonObject = QQHttpClient.getUserInfo(url);
            userDO.setName((String) jsonObject.get("nickname"));
            userDO.setPhoto((String) jsonObject.get("figureurl_qq_2"));
        }
        return getUser(openId, userDO);
    }

    @Override
    public BaseVO weiboBack(String code) throws IOException {
        Map<String, String> params = new HashMap<>(5);
        //不知道url填什么可以看文档：https://open.weibo.com/wiki/Oauth2/access_token
        String url = "https://api.weibo.com/oauth2/access_token";
        //申请应用时分配的AppKey
        params.put("client_id", clientId);
        //申请应用时分配的AppSecret
        params.put("client_secret", clientSecret);
        //请求的类型，填写authorization_code
        params.put("grant_type", "authorization_code");
        //调用authorize获得的code值
        params.put("code", code);
        //回调地址，需与注册应用里的回调地址一致。
        params.put("redirect_uri", weiboBackUrl);
        String result = WeiBoHttpUtil.post(url, params);
        JSONObject jsonObject = (JSONObject) JSONObject.parse(result);
        url = "https://api.weibo.com/2/users/show.json";
        String userInfo = WeiBoHttpUtil.get(url, jsonObject.get("access_token"), jsonObject.get("uid"));
        log.info(" 得到的用户信息为：[{}]", userInfo);
        jsonObject = (JSONObject) JSONObject.parse(userInfo);
        UserDO userDO = new UserDO();
        userDO.setState("0");
        userDO.setCreateTime(new Date());
        userDO.setPoint(5);
        userDO.setIsMember("0");
        userDO.setOpenId(((Long) jsonObject.get("id")).toString());
        userDO.setName((String) jsonObject.get("screen_name"));
        userDO.setPhoto((String) jsonObject.get("profile_image_url"));
        return getUser(userDO.getOpenId(), userDO);
    }

    private BaseVO getUser(String openId, UserDO user) {
        UserTokenVO userTokenVO = new UserTokenVO();
        UserVO userVO = new UserVO();
        //查询是否已经注册
        UserDO userDO = userMapper.selectOne(new QueryWrapper<UserDO>().eq("qq_id", openId));
        if (null != userDO) {
            //账户是否锁定
            if (!userDO.getState().equals("0")) {
                return new BaseVO(false, ErrorCodeEnum.E0707.getKey(), ErrorCodeEnum.E0707.getValue());
            }
        } else {
            //未注册
            userMapper.save(user);
            //获取添加成功的id
            userDO.setId(userDO.getId());
        }
        userTokenVO.setToken(JWTUtil.createToken(String.valueOf(userDO.getId()), user.getUsername()));
        BeanUtils.copyProperties(user, userVO);
        userTokenVO.setUserVO(userVO);
        return userTokenVO;
    }
}