package com.xiyan.service;

import com.xiyan.bo.PaySaPiBO;

import java.io.UnsupportedEncodingException;

/**
 * @author : bright
 * @Description :
 * @date:Created in 2021/1/30 0030 10:26
 */
public interface PayService {
    /**
     * 支付回调
     *
     * @return
     */
    String back(PaySaPiBO paySaPiBO) throws UnsupportedEncodingException;
}
