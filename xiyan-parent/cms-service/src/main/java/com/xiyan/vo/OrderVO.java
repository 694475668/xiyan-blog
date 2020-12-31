package com.xiyan.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author bright
 */
@Data
public class OrderVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单号")
    private String payId;

    @ApiModelProperty(value = "支付类型")
    private String type;

    @ApiModelProperty(value = "密钥")
    private String sign;

    @ApiModelProperty(value = "额外参数")
    private String param;

    @ApiModelProperty(value = "金额")
    private BigDecimal price;

    @ApiModelProperty(value = "是否是html")
    private Integer isHtml;
}