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
@TableName("t_article")
public class ArticleDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章封面
     */
    private String pic;

    /**
     * 文章标签
     */
    private String tag;

    /**
     * 简介
     */
    private String remark;

    /**
     * 文章阅读量
     */
    private Integer readCount;

    /**
     * 文章审核状态
     */
    private String state;

    /**
     * 文章内容 （转换html的内容）
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
     * 文章作者id
     */
    private Integer userId;

    /**
     * 文章类型
     */
    private String type;

    /**
     * 好文
     */
    private String good;
    /**
     * 推荐
     */
    private String recommend;
    /**
     * 官方
     */

    private String official;
    /**
     * 点赞数
     */
    private Integer starCount;

    /**
     * 评论数
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