package com.xiyan.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author bright
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class RoleResourceVO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资源ID")
    private Integer id;

    @ApiModelProperty(value = "资源名称")
    private String name;
}
