package com.xiyan.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author bright
 */
@Data
public class OrderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "金额")
    @NotNull(message = "金额不能为空")
    private BigDecimal price;

    @ApiModelProperty(value = "支付类型")
    @NotBlank(message = "支付类型不能为空")
    private String type;

}