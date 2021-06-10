package com.xiyan.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author bright
 */
@Data
@TableName("t_resource")
public class ResourceDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 资源ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 资源备注
     */
    private String remark;

    /**
     * 资源序号
     */
    private Integer seq;

    /**
     * 资源链接
     */
    private String url;

    /**
     * 资源类型原值
     */
    private String type;

    /**
     * 资源创建时间
     */
    private Date createTime;
    /**
     * 资源创建人
     */
    private String createUser;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 资源最后更新人
     */
    private String updateUser;

    /**
     * 父级ID
     */
    private Integer pid;


    @TableField(exist = false)
    private String meaning;
}
