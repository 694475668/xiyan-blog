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

    E0601("E0701", "已收藏"),
    ;

    private String key;
    private String value;
}
