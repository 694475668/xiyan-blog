package com.xiyan.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/**
 * @author: bright
 * @date:Created in 2021/03/22 0011 22:30
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RoleVagueDTO extends PageDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色名称")
    private String name;
}
