package com.xiyan.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiyan.domain.UserDO;
import com.xiyan.enumeration.ErrorCodeEnum;
import com.xiyan.mapper.UserMapper;
import com.xiyan.service.WeiBoService;
import com.xiyan.util.JWTUtil;
import com.xiyan.util.WeiBoHttpUtil;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.UserTokenVO;
import com.xiyan.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : bright
 * @Description :
 * @date:Created in 2021/1/29 0029 21:52
 */
@Service
@Slf4j
public class WeiBoServiceImpl implements WeiBoService {
    @Resource
    private UserMapper userMapper;

    @Value("${weibo.client_id}")
    private String clientId;

    @Value("${weibo.client_secret}")
    private String clientSecret;

    @Value("${weibo.back}")
    private String weiboBackUrl;

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
        userDO.setUpdateTime(new Date());
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
