package com.xiyan.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author bright
 */
@Data
public class TalkListVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "阅读量")
    private Integer readCount;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "回答数")
    private Integer conCount;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "发布人")
    private String name;

    @ApiModelProperty(value = "发布人头像")
    private String photo;

}