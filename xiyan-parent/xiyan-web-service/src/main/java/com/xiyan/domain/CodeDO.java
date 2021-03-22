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
@TableName("t_code")
public class CodeDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 简介
     */
    private String remark;

    /**
     * 封面
     */
    private String pic;

    /**
     * 标签
     */
    private String tag;

    /**
     * 下载地址
     */
    private String downloadUrl;

    /**
     * 下载金币
     */
    private Integer downloadPoint;

    /**
     * 阅读量
     */
    private Integer readCount;

    /**
     * 审核状态
     */
    private String state;

    /**
     * 内容 （转换html的内容）
     */
    private String content;

    /**
     * markdown 未转换html的内容
     */
    private String markdownContent;

    /**
     * 0. mavon-editor 1.editor-wang
     */
    private String markdownType;
    /**
     * 精品
     */
    private String boutique;
    /**
     * 推荐
     */
    private String recommend;
    /**
     * 官方
     */
    private String official;

    /**
     * 置顶
     */
    private String top;

    /**
     * 发布人id
     */
    private Integer userId;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 类型
     */
    private String type;

    /**
     * 点赞数
     */
    private Integer starCount;

    /**
     * 评论数
     */
    private Integer conCount;

    /**
     * 下载数
     */
    private Integer downloadCount;

}