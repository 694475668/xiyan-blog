package com.xiyan.enumeration;

/**
 * Description: 异常枚举枚举类
 *
 * @version 1.0:
 * @author: bright
 * @date:Created in 2020/7/3
 */
public enum ErrorCodeEnum {

    E0804("E0804", "传入的文件名不能为空."),
    E0805("E0805", "文件名应仅包含汉字、字母、数字、下划线和点号."),
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
