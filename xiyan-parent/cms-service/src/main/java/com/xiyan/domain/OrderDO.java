package com.xiyan.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author bright
 */
@Data
@TableName("t_order")
public class OrderDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 金额
     */
    private BigDecimal price;

    /**
     * 类型：1：充值 2：提现
     */
    private String type;

    /**
     * 0：待完成 1：已完成
     */
    private String status;

    /**
     * 支付方式 1.微信 2.支付宝
     */
    private String payType;

    /**
     * 充值人
     */
    private Integer userId;

    /**
     * 充值金币
     */
    private Integer point;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

}