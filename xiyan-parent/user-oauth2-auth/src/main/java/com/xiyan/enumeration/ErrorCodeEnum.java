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

    E0851("E0851", "邮箱已经注册！！！！"),
    E0852("E0852", "验证码已过期！！！！"),
    E0853("E0853", "验证码错误！！！！"),
    E0854("E0854", "邮箱或密码不正确！！！！"),
    E0855("E0855", "邮箱不存在！！！！"),
    E0856("E0856", "系统无法识别为有效参数！！！！"),
    E0857("E0857", "该账号已锁定！！！！"),
    E0858("E0858", "未登录或者用户token无效或已过期!"),
    E0859("E0859", "该邮箱已被其它账户绑定!"),
    ;

    private String key;
    private String value;

}
