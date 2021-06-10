package com.xiyan.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author bright
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class ResourceTreeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资源ID")
    private Integer id;

    @ApiModelProperty(value = "资源名称")
    private String label;

    @ApiModelProperty(value = "默认展开")
    private String state = "open";

    @ApiModelProperty(value = "默认不选中")
    private Boolean checked = false;

    @ApiModelProperty(value = "自定义属性")
    private Map<String, Object> attributes;

    @ApiModelProperty(value = "下级资源")
    private List<ResourceTreeVO> children;

    @ApiModelProperty(value = "上级资源ID")
    private Integer pid;
}
