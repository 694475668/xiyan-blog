package com.xiyan.constants;


/**
 * Description: 常量类
 *
 * @version 1.0:
 * @author: bright
 * @date:Created in 2020/7/3
 */
public class Constant {

    /**
     * token失效时间 1天
     */
    public static final int JWT_TTL = 86400;

    /**
     * token密钥
     */
    public static final String SECRET = "xiyan";

    public static final String LOCK_STATE = "1";

    /**
     * 认证资源
     */
    public static final String AUTH_USER_KEY = "xiyan_user_";

    /**
     * 黑名单redis列表标识
     */
    public static final String BLACK_LIST_KEY = "black-list";
}
