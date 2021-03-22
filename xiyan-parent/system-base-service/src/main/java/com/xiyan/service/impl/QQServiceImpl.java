package com.xiyan.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiyan.domain.UserDO;
import com.xiyan.enumeration.ErrorCodeEnum;
import com.xiyan.mapper.UserMapper;
import com.xiyan.service.QQService;
import com.xiyan.util.JWTUtil;
import com.xiyan.util.QQHttpClient;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.QQRequestVO;
import com.xiyan.vo.UserTokenVO;
import com.xiyan.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.UUID;

/**
 * @author : bright
 * @Description :
 * @date:Created in 2021/1/29 0029 21:52
 */
@Service
@Slf4j
public class QQServiceImpl implements QQService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Value("${qq.back}")
    private String qqBackUrl;

    @Value("${qq.app_id}")
    private String appId;

    @Value("${qq.app_key}")
    private String appKey;

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
            return new BaseVO(false, ErrorCodeEnum.E0856.getKey(), ErrorCodeEnum.E0856.getValue());
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
        userDO.setUpdateTime(new Date());
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
    private BaseVO getUser(String openId, UserDO user) {
        UserTokenVO userTokenVO = new UserTokenVO();
        UserVO userVO = new UserVO();
        Integer userId = null;
        //查询是否已经注册
        UserDO userDO = userMapper.selectOne(new QueryWrapper<UserDO>().eq("open_id", openId));
        if (null != userDO) {
            //账户是否锁定
            if (!userDO.getState().equals("0")) {
                return new BaseVO(false, ErrorCodeEnum.E0857.getKey(), ErrorCodeEnum.E0857.getValue());
            }
            userId = userDO.getId();
            user = userDO;
        } else {
            //未注册
            userMapper.insert(user);
            userId = user.getId();
        }
        userTokenVO.setToken(JWTUtil.createToken(String.valueOf(userId), user.getUsername()));
        BeanUtils.copyProperties(user, userVO);
        userTokenVO.setUserVO(userVO);
        return userTokenVO;
    }
}
