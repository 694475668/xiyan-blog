package com.xiyan.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author: bright
 * @date:Created in 2020/12/1 14:24
 */
@Data
public class SearchDTO extends PageDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "关键字", required = true)
    private String keywords;
}
