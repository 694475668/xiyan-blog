package com.xiyan.service;

import com.xiyan.dto.OrderDTO;
import com.xiyan.vo.BaseVO;

import java.io.UnsupportedEncodingException;

/**
 * Description:
 *
 * @author: bright
 * @date:Created in 2020/12/23 10:11
 */
public interface OrderService {

    /**
     * 创建订单
     *
     * @param orderDTO
     * @param id
     * @return
     */
    BaseVO create(OrderDTO orderDTO, Integer id) throws UnsupportedEncodingException;
}
