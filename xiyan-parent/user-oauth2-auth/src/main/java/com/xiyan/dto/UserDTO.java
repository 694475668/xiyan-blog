package com.xiyan.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author: bright
 * @date:Created in 2020/11/8 14:33
 * @describe :
 */
@Data
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "邮箱")
    @NotBlank(message = "邮箱不能为空")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "验证码")
    @NotBlank(message = "验证码不能为空")
    private String code;

}
