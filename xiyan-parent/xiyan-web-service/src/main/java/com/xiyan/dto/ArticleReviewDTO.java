package com.xiyan.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author bright
 */
@Data
public class ArticleReviewDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "审核状态")
    @NotBlank(message = "审核状态不能为空")
    private String state;

}