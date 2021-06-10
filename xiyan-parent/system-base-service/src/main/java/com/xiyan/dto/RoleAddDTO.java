package com.xiyan.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @version :
 * @author: bright
 * @date:Created in 2021/3/25 0011 10:30
 */
@Data
@ToString(callSuper = true)
public class RoleAddDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色名称", required = true)
    @NotBlank(message = "角色名称不能为空")
    private String name;

    @ApiModelProperty(value = "角色描述")
    private String remark;

    @ApiModelProperty(value = "id权限列表")
    @NotEmpty(message = "id权限列表不能为空")
    private Integer[] idList;
}
