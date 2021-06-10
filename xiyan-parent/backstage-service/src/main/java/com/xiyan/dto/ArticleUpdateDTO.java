package com.xiyan.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author bright
 */
@Data
public class ArticleUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "好文")
    private String good;

    @ApiModelProperty(value = "推荐")
    private String recommend;

    @ApiModelProperty(value = "官方")
    private String official;

}