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

    E0751("E0751", "用户已经存在!"),
    E0752("E0752", "用户不可以删除自己!"),
    E0753("E0753", "请先删除该用户下的所有子用户!"),
    E0754("E0754", "角色下尚有用户不允许删除!"),
    E0755("E0755", "请先删除该角色下的所有子角色!"),
    E0756("E0756", "请先删除子节点后在删除当前节点!"),
    E0757("E0757", "该资源已有角色使用，不可删除"),
    E0758("E0758", "同一个业务码下不允许存在相同编码！"),
    E0759("E0759", "用户名或密码不正确！！！！"),
    E0760("E0760", "用户名不存在！！！！"),
    E0761("E0761", "该账号已锁定！！！！"),
    E0762("E0762", "未登录或者用户token无效或已过期!"),
    ;

    private String key;
    private String value;
}
