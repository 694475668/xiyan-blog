package com.xiyan.constants;

/**
 * @author : bright
 * @version : 1.0
 * @describe :
 * @date:Created in 2020/9/17 10:30
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

    /**
     * 加密方式
     */
    public static final String ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * 如果为0不加密  为空加密
     */
    public static final String ENCRYPTION_FLAG = "0";
}
