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

    public static final String LOCK_STATE = "0";

    /**
     * 加密方式
     */
    public static final String ALGORITHM = "AES/ECB/PKCS5Padding";


}
