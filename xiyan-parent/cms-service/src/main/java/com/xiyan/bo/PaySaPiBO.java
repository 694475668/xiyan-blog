package com.xiyan.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 支付回调的参数实体类
 *
 * @author bright
 */
@Data
public class PaySaPiBO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 订单ID号
     */
    private String payId;

    /**
     * 自定义参数
     */
    private String param;

    /**
     * 订单定价
     */
    private String price;

    /**
     * 实际支付金额
     */
    private String reallyPrice;

    /**
     * 支付类型
     */
    private String type;

    /**
     * 密钥
     */
    private String sign;

}
