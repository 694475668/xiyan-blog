package com.xiyan.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @version :
 * @author: 刘 明
 * @date:Created in 2021-03-17 13:39
 */
@Data
public class UserAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "姓名")
    @NotBlank(message = "姓名不能为空")
    private String name;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @ApiModelProperty(value = "登录密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "id列表", required = true)
    @NotEmpty(message = "id列表不能为空")
    private Integer[] idList;
}
