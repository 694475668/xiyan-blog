package com.xiyan.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * @author: bright
 * @date:Created in 2021/3/15 0015 21:41
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class RoleListVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID")
    private Integer id;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "所拥有操作资源集合对象")
    private List<RoleResourceVO> resourceList;
}
