package com.xiyan.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author bright
 */
@Data
public class CodeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章封面")
    private String pic;

    @ApiModelProperty(value = "简介")
    private String remark;

    @ApiModelProperty(value = "文章标签集合")
    private List<String> tagList;

    private String tag;

    @ApiModelProperty(value = "下载金币")
    private Integer downloadPoint;

    @ApiModelProperty(value = "文章阅读量")
    private Integer readCount;

    @ApiModelProperty(value = "文章内容")
    private String content;

    @ApiModelProperty(value = "精品")
    private String boutique;

    @ApiModelProperty(value = "置顶")
    private String top;

    @ApiModelProperty(value = "推荐")
    private String recommend;

    @ApiModelProperty(value = "官方")
    private String official;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "点赞数")
    private Integer starCount;

    @ApiModelProperty(value = "评论数")
    private Integer conCount;

    @ApiModelProperty(value = "下载数")
    private Integer downloadCount;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "发布人")
    private String name;

}