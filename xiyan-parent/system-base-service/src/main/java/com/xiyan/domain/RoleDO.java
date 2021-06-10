package com.xiyan.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author bright
 */
@Data
@TableName("t_role")
public class RoleDO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 角色ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色描述
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 创建人员
     */
    private String createUser;
    /**
     * 修改人员
     */
    private String updateUser;

    /**
     * 父级
     */
    private Integer pid;
}
