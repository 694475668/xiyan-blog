package com.xiyan.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: bright
 * @date:Created in 2020/11/21 19:20
 * @describe :
 */
@Data
public class QQRequestVO extends BaseVO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "QQ请求地址")
    private String url;
}
