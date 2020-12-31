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
public class SearchVO implements Serializable {

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

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "发布人")
    private String name;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

}