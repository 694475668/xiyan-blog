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
@TableName("t_talk")
public class TalkDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    private String title;


    /**
     * 阅读量
     */
    private Integer readCount;


    /**
     * 内容 （转换html的内容）
     */
    private String content;


    /**
     * 作者id
     */
    private Integer userId;

    /**
     * 类型
     */
    private String type;

    /**
     * 回答数
     */
    private Integer conCount;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

}