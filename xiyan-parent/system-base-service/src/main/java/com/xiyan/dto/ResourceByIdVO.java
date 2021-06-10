package com.xiyan.dto;

import com.xiyan.vo.BaseVO;
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
public class ResourceByIdVO extends BaseVO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资源ID")
    private Integer id;

    @ApiModelProperty(value = "资源名称")
    private String name;

    @ApiModelProperty(value = "资源备注")
    private String remark;

    @ApiModelProperty(value = "资源序号")
    private Integer seq;

    @ApiModelProperty(value = "资源链接")
    private String url;

    @ApiModelProperty(value = "父级ID")
    private Integer pid;

    @ApiModelProperty(value = "资源类型")
    private String type;

    @ApiModelProperty(value = "父级名称")
    private String pName;
}
