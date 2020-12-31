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
@Document(indexName = "article")
public class ArticleBO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    /**
     * 文章标题
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;

    /**
     * 文章封面
     */
    @Field(type = FieldType.Text)
    private String pic;

    /**
     * 文章标签
     */
    @Field(type = FieldType.Keyword)
    private String tag;

    /**
     * 简介
     */
    @Field(type = FieldType.Keyword)
    private String remark;

    /**
     * 文章阅读量
     */
    private Integer readCount;

    /**
     * 文章审核状态
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
     * 文章作者id
     */
    private Integer userId;

    /**
     * 文章类型
     */
    @Field(type = FieldType.Keyword)
    private String type;

    /**
     * 好文
     */
    @Field(type = FieldType.Keyword)
    private String good;
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