package com.xiyan.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @version :
 * @author: bright
 * @date:Created in 2021-03-20 14:21
 */
@Data
public class UserGrantDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id列表")
    @NotNull(message = "id列表不能为空")
    private Integer userId;

    @ApiModelProperty(value = "角色id列表")
    @NotEmpty(message = "id列表不能为空")
    private Integer[] roleIdList;
}
