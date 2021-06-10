package com.xiyan.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @version :
 * @author: bright
 * @date:Created in 2021-03-17 13:39
 */
@Data
public class UserPwdDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID", required = true)
    @NotNull(message = "用户ID不能为空")
    private Integer id;

    @ApiModelProperty(value = "用户密码", required = true)
    @NotBlank(message = "用户密码不能为空")
    private String password;
}
