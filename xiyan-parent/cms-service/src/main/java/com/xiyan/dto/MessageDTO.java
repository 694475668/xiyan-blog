package com.xiyan.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author: bright
 * @date:Created in 2020/11/15 9:11
 * @describe :
 */
@Data
public class MessageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "内容")
    @NotBlank(message = "内容不能为空")
    private String content;
}
