package com.xiyan.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @version :
 * @author: 刘 明
 * @date:Created in 2012-7-7 16:56
 */
@Data
public class PageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "起始页", required = true)
    @NotNull(message = "起始页不能为空")
    private Integer pageNo;

    @ApiModelProperty(value = "页大小", required = true)
    @NotNull(message = "页大小不能为空")
    private Integer pageSize;
}
