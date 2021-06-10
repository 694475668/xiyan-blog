package com.xiyan.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: bright
 * @date:Created in 2020/11/8 14:33
 * @describe :
 */
@Data
@TableName("t_user")
public class UserDO implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String name;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 收集
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态
     */
    private String status;
    /**
     * 最后登录时间
     */
    private Date lastLoginDate;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private String createUser;


    /**
     * 父级
     */
    private Integer pid;

    /**
     * 修改人
     */
    private String updateUser;

    @TableField(exist = false)
    private String rIds;

    @TableField(exist = false)
    private String rNames;

}
