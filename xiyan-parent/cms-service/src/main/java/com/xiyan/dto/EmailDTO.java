package com.xiyan.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author: bright
 * @date:Created in 2020/11/8 14:43
 * @describe :
 */
@Data
public class EmailDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户邮箱")
    @NotBlank(message = "邮箱不能为空")
    private String username;
}
