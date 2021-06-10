package com.xiyan.hander;


import com.xiyan.domain.UserDO;
import com.xiyan.util.JWTUtil;
import com.xiyan.util.ResponseUtil;
import com.xiyan.vo.UserAuthVO;
import com.xiyan.vo.UserTokenVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: 登录成功处理类
 * @Author: bright
 * @Date: 2020/4/28
 **/
@Slf4j
@Component
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {
    /**
     * 登录成功返回结果
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 获取登陆的用户信息
        UserDO user = (UserDO) authentication.getPrincipal();
        UserAuthVO userAuthVO = new UserAuthVO();
        BeanUtils.copyProperties(user, userAuthVO);
        //生成令牌
        String token = JWTUtil.createToken(String.valueOf(user.getId()), user.getUsername());
        //写入到vo
        UserTokenVO userTokenVO = new UserTokenVO(token, userAuthVO);
        //响应给前端
        ResponseUtil.ResponseMeg(response, userTokenVO);
    }
}
