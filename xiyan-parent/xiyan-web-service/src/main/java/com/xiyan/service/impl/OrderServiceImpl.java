package com.xiyan.service.impl;

import com.xiyan.constants.Constant;
import com.xiyan.domain.OrderDO;
import com.xiyan.dto.OrderDTO;
import com.xiyan.enumeration.ErrorCodeEnum;
import com.xiyan.mapper.OrderMapper;
import com.xiyan.service.OrderService;
import com.xiyan.util.OrderNumberGenerationUtil;
import com.xiyan.util.PayUtil;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.OrderVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Description:
 *
 * @author: bright
 * @date:Created in 2020/12/23 10:22
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;

    @Override
    public BaseVO create(OrderDTO orderDTO, Integer id) throws UnsupportedEncodingException {
        OrderVO orderVO = new OrderVO();
        OrderDO orderDO = new OrderDO();
        Integer point = null;
        orderDO.setIsMember(Constant.ORDINARY);
        if (orderDTO.getPrice().compareTo(new BigDecimal(10)) == 0) {
            orderDO.setPrice(orderDTO.getPrice());
            point = 100;
        } else if (orderDTO.getPrice().compareTo(new BigDecimal(30)) == 0) {
            orderDO.setPrice(orderDTO.getPrice());
            point = 500;
        } else if (orderDTO.getPrice().compareTo(new BigDecimal(50)) == 0) {
            orderDO.setPrice(orderDTO.getPrice());
            point = 1000;
        } else if (orderDTO.getPrice().compareTo(new BigDecimal(100)) == 0) {
            orderDO.setPrice(orderDTO.getPrice());
            point = 3000;
        } else if (orderDTO.getPrice().compareTo(new BigDecimal(199)) == 0) {
            orderDO.setPrice(orderDTO.getPrice());
            point = 0;
            orderDO.setIsMember(Constant.MEMBER);
        } else {
            return new BaseVO(false, ErrorCodeEnum.E0706.getKey(), ErrorCodeEnum.E0706.getValue());
        }
        String orderId = OrderNumberGenerationUtil.getOrderId();
        orderDO.setOrderId(orderId);
        orderDO.setPayType(orderDTO.getType());
        orderDO.setPrice(orderDTO.getPrice());
        orderDO.setStatus("1");
        orderDO.setType("1");
        orderDO.setUserId(id);
        orderDO.setPoint(point);
        orderDO.setCreateTime(new Date());
        orderMapper.insert(orderDO);
        orderVO.setPayId(orderId);
        orderVO.setType(orderDTO.getType());
        orderVO.setPrice(orderDTO.getPrice());
        orderVO.setIsHtml(1);
        orderVO.setSign(PayUtil.getSign(orderVO));
        orderVO.setUrl(Constant.URL);
        return orderVO;
    }
}
