package com.xiyan.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author bright
 */
@Data
public class CodeDownloadVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "下载地址")
    private String url;

    @ApiModelProperty(value = "购买用户剩余金币")
    private Integer point;
}