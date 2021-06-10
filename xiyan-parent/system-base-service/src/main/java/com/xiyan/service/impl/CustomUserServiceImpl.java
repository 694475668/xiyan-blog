package com.xiyan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiyan.constants.Constant;
import com.xiyan.domain.UserDO;
import com.xiyan.mapper.UserMapper;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @version :
 * @author: bright
 * @date:Created in 2020/6/24 18:43
 */
@Service
public class CustomUserServiceImpl implements AuthenticationProvider {


    @Resource
    private UserMapper userMapper;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取表单输入中返回的用户名
        String userName = (String) authentication.getPrincipal();
        // 获取表单中输入的密码
        String password = (String) authentication.getCredentials();
        //获取用户信息
        UserDO user = userMapper.selectOne(new QueryWrapper<UserDO>().eq("username", userName));
        if (null == user) {
            //仍需要细化处理
            throw new UsernameNotFoundException("该用户不存在");
        }
        // 我们还要判断密码是否正确，这里我们的密码使用BCryptPasswordEncoder进行加密的
        if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            throw new BadCredentialsException("密码不正确");
        }
        // 还可以加一些其他信息的判断，比如用户账号已停用等判断
        if (!Constant.LOCK_STATE.equals(user.getStatus())) {
            throw new LockedException("该用户已被锁定");
        }
        return new UsernamePasswordAuthenticationToken(user, password);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
