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

    //编辑器类型
    public static final String WANG_TYPE = "1";

    /**
     * 通讯密钥
     */
    public static String APP_SECRET;
    /**
     * 支付地址
     */
    public static String URL;

    /**
     * 普通
     */
    public static final String ORDINARY = "0";
    /**
     * 会员
     */
    public static final String MEMBER = "1";
}
