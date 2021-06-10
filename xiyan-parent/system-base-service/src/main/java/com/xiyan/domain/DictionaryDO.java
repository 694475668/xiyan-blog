package com.xiyan.domain;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("t_dictionary")
public class DictionaryDO implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 数据名称
     */
    private String name;
    /**
     * 数据值
     */
    private String value;
    /**
     * 含义
     */
    private String meaning;

    private String createUser;
    private String updateUser;

    /**
     * 备注
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

}
