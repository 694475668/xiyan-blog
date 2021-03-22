package com.xiyan.enumeration;

/**
 * Description: 异常枚举枚举类
 *
 * @version 1.0:
 * @author: bright
 * @date:Created in 2020/7/3
 */
public enum ErrorCodeEnum {

    E0701("E0701", "已收藏"),
    E0702("E0702", "未收藏"),
    E0703("E0703", "已关注"),
    E0704("E0704", "未关注"),
    E0705("E0705", "金币不足"),
    E0706("E0706", "非法参数"),
    E0707("E0707", "自己不能评论自己"),
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
