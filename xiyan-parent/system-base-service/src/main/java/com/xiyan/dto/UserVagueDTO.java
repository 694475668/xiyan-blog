package com.xiyan.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/**
 * @version 1.0:
 * @author: bright
 * @date:Created in 2020/7/7
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserVagueDTO extends PageDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "角色id")
    private String roleId;
}
