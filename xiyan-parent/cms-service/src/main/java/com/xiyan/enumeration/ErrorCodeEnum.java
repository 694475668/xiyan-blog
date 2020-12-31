package com.xiyan.enumeration;

/**
 * Description: 异常枚举枚举类
 *
 * @version 1.0:
 * @author: bright
 * @date:Created in 2020/7/3
 */
public enum ErrorCodeEnum {

    E0701("E0701", "邮箱已经注册！！！！"),
    E0702("E0702", "验证码已过期！！！！"),
    E0703("E0703", "验证码错误！！！！"),
    E0704("E0704", "邮箱或密码不正确！！！！"),
    E0705("E0705", "邮箱不存在！！！！"),
    E0706("E0706", "系统无法识别为有效参数！！！！"),
    E0707("E0707", "该账号已锁定！！！！"),
    E0708("E0708", "已收藏"),
    E0709("E0709", "未收藏"),
    E0710("E0710", "已关注"),
    E0711("E0711", "未关注"),
    E0712("E0712", "金币不足"),
    E0713("E0713", "非法参数"),
    E0714("E0714", "自己不能评论自己"),
    ;

    private String key;
    private String value;

    ErrorCodeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }


    public String getValue() {
        return value;
    }
}
