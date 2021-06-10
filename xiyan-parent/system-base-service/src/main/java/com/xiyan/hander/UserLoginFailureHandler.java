package com.xiyan.hander;


import com.xiyan.enumeration.ErrorCodeEnum;
import com.xiyan.util.ResponseUtil;
import com.xiyan.vo.BaseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败处理类
 *
 * @author bright
 */
@Slf4j
@Component
public class UserLoginFailureHandler implements AuthenticationFailureHandler {
    /**
     * 登录失败返回结果
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        // 这些对于操作的处理类可以根据不同异常进行不同处理
        if (exception instanceof UsernameNotFoundException) {
            log.info("【登录失败】" + exception.getMessage());
            //用户名不存在
            ResponseUtil.ResponseMeg(response, new BaseVO(false, ErrorCodeEnum.E0760.getKey(), ErrorCodeEnum.E0760.getValue()));
            return;
        }
        if (exception instanceof LockedException) {
            log.info("【登录失败】" + exception.getMessage());
            //用户锁定
            ResponseUtil.ResponseMeg(response, new BaseVO(false, ErrorCodeEnum.E0761.getKey(), ErrorCodeEnum.E0761.getValue()));
            return;
        }
        if (exception instanceof BadCredentialsException) {
            log.info("【登录失败】" + exception.getMessage());
            //密码错误
            ResponseUtil.ResponseMeg(response, new BaseVO(false, ErrorCodeEnum.E0759.getKey(), ErrorCodeEnum.E0759.getValue()));
            return;
        }
    }
}
