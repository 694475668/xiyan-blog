package com.xiyan.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * @author bright
 */
@Data
public class RoleByIdVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID")
    private Integer id;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "角色描述")
    private String remark;

    @ApiModelProperty(value = "当前角色所属资源集合")
    private List<RoleResourceVO> resourceList;
}
