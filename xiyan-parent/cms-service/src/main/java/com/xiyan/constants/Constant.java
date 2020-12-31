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

    /**
     * redis aes的key
     */
    public static final String AES_KEY = "aes-keypair";

    public static String AES_KEYPAIR;

    /**
     * 加密方式
     */
    public static final String ALGORITHM = "AES/ECB/PKCS5Padding";
    /**
     * 1.blog 2.code 3.shuoshuo
     */
    public static final String COMMENT_TYPE_BLOG = "1";
    public static final String COMMENT_TYPE_CODE = "2";
    public static final String COMMENT_TYPE_TALK = "3";

    /**
     * 通讯密钥
     */
    public static String APP_SECRET = "26cf60aa39c60157f6ace8e1460d2396";
}
