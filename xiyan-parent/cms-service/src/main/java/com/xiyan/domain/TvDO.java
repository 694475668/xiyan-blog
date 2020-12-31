package com.xiyan.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author bright
 */
@Data
@TableName("t_tv")
public class TvDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 电视名
     */
    private String name;

    /**
     * 类型
     */
    private String type;

    /**
     * 流地址
     */
    private String url;

}