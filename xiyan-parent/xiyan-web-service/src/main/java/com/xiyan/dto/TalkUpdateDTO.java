package com.xiyan.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author bright
 */
@Data
public class TalkUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标题", required = true)
    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty(value = "内容", required = true)
    @NotBlank(message = "内容不能为空")
    private String content;

    @ApiModelProperty(value = "类型", required = true)
    @NotBlank(message = "标类型不能为空")
    private String type;
}