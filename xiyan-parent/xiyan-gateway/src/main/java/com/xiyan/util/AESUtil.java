package com.xiyan.util;


import com.xiyan.constants.Constant;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES工具类
 *
 * @author bright
 */
@Log4j2
public class AESUtil {

    public static void main(String[] args) throws Exception {

        String name = "{\"email\":\"694475668@qq.com\",\"password\":\"admin\"}";
        String encodeRule = "Tl2mds9xtfQE4n0VZ9TqYg8SQaJPvzGj";
        System.out.println(encodeRule);
        String enResult = aesEncrypt(name, encodeRule);
        System.out.println("加密后：=======" + enResult);
        String deResult = aesDecrypt(enResult, encodeRule);
        System.out.println("解密后：=======" + deResult);
    }

    /**
     * 获取AES编码规则
     *
     * @return
     */
    public static String genEncodeRule() {
        StringBuilder chars = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            int num = Integer.parseInt(String.valueOf(Math.round(Math.floor(Math.random() * chars.length()))));
            result.append(chars.charAt(num));
        }
        return result.toString();
    }

    /**
     * 加密
     *
     * @param content
     * @param encryptKey
     * @return
     * @throws Exception
     */
    public static String aesEncrypt(String content, String encryptKey) throws Exception {
        return base64Encode(aesEncryptToBytes(content, encryptKey));
    }

    public static String base64Encode(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(Constant.ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
        return cipher.doFinal(content.getBytes("utf-8"));
    }

    /**
     * 解密
     *
     * @param encryptStr
     * @param decryptKey
     * @return
     * @throws Exception
     */
    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
        return aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
    }

    public static byte[] base64Decode(String base64Code) {
        return Base64.decodeBase64(base64Code);
    }

    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(Constant.ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }
}
