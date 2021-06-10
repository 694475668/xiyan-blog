package com.xiyan.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 注册时生成密码工具类
 *
 * @version :
 * @author: bright
 * @date:Created in 2020/6/25 8:48
 */
public class BcryptPasswordUtil {
    public static String createBCryptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
