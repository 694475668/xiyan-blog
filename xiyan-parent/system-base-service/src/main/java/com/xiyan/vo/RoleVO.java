package com.xiyan.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: bright
 * @date:Created in 2020/11/8 14:33
 * @describe :
 */
@Data
public class RoleVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "角色名称")
    private String name;
}
