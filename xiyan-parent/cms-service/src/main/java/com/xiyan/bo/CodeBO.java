package com.xiyan.bo;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * @author bright
 */
@Data
@Accessors(chain = true)
@Document(indexName = "code")
public class CodeBO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    /**
     * 标题
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;

    /**
     * 简介
     */
    @Field(type = FieldType.Keyword)
    private String remark;

    /**
     * 封面
     */
    @Field(type = FieldType.Text)
    private String pic;

    /**
     * 标签
     */
    @Field(type = FieldType.Keyword)
    private String tag;

    /**
     * 下载地址
     */
    @Field(type = FieldType.Keyword)
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
    @Field(type = FieldType.Keyword)
    private String state;

    /**
     * 内容 （转换html的内容）
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String content;

    /**
     * markdown 未转换html的内容
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String markdownContent;

    /**
     * 0. mavon-editor 1.editor-wang
     */
    @Field(type = FieldType.Keyword)
    private String markdownType;

    /**
     * 精品
     */
    @Field(type = FieldType.Keyword)
    private String boutique;
    /**
     * 推荐
     */
    @Field(type = FieldType.Keyword)
    private String recommend;
    /**
     * 官方
     */
    @Field(type = FieldType.Keyword)
    private String official;

    /**
     * 置顶
     */
    @Field(type = FieldType.Keyword)
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
    @Field(type = FieldType.Keyword)
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