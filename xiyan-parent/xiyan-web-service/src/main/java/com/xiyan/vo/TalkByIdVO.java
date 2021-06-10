package com.xiyan.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author bright
 */
@Data
public class TalkByIdVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "类型")
    private String type;
}