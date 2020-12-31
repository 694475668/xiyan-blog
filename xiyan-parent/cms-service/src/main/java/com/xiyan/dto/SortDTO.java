package com.xiyan.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Description:
 *
 * @author: bright
 * @date:Created in 2020/12/1 14:24
 */
@Data
public class SortDTO extends PageDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "排序字段", required = true)
    @NotBlank(message = "排序字段不能为空")
    private String sortField;
}
