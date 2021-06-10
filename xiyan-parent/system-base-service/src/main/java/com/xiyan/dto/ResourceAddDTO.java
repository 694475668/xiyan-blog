package com.xiyan.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author bright
 */
@Data
public class ResourceAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资源名称", required = true)
    @NotBlank(message = "资源名称不能为空")
    private String name;

    @ApiModelProperty(value = "资源地址")
    private String url;

    @ApiModelProperty(value = "资源类型ID")
    @NotBlank(message = "资源类型不能为空")
    private String type;

    @ApiModelProperty(value = "资源序号")
    private Integer seq;

    @ApiModelProperty(value = "父级ID")
    private Integer pid;

    @ApiModelProperty(value = "资源备注")
    private String remark;
}
