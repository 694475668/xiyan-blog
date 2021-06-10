package com.xiyan.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description: 异常枚举枚举类
 *
 * @version 1.0:
 * @author: bright
 * @date:Created in 2020/7/3
 */
@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {

    E0751("E0751", "传入的文件名不能为空."),
    E0752("E0752", "文件名应仅包含汉字、字母、数字、下划线和点号."),
    ;

    private String key;
    private String value;

}
