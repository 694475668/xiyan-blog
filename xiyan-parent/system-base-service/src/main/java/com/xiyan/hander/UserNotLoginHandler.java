package com.xiyan.hander;

import com.xiyan.enumeration.ErrorCodeEnum;
import com.xiyan.util.ResponseUtil;
import com.xiyan.vo.BaseVO;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 用户未登录处理类
 * @Author: bright
 * @Date: 2020/4/28
 **/
@Component
public class UserNotLoginHandler implements AuthenticationEntryPoint {
    /**
     * 用户未登录返回结果
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        ResponseUtil.ResponseMeg(response, new BaseVO(false, ErrorCodeEnum.E0762.getKey(), ErrorCodeEnum.E0762.getValue()));
    }
}