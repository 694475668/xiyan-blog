package com.xiyan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiyan.bo.PaySaPiBO;
import com.xiyan.constants.Constant;
import com.xiyan.domain.OrderDO;
import com.xiyan.dto.GetUserDTO;
import com.xiyan.dto.UserUpdateDTO;
import com.xiyan.feign.UserFeign;
import com.xiyan.mapper.OrderMapper;
import com.xiyan.service.PayService;
import com.xiyan.util.PayUtil;
import com.xiyan.vo.UserByIdVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author : bright
 * @Description :
 * @date:Created in 2021/1/30 0030 10:28
 */
@Service
public class PayServiceImpl implements PayService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private UserFeign userFeign;

    @Override
    public String back(PaySaPiBO paySaPiBO) throws UnsupportedEncodingException {
        // 保证密钥一致性
        if (PayUtil.checkPayKey(paySaPiBO)) {
            String payId = paySaPiBO.getPayId();
            OrderDO orderDO = new OrderDO();
            orderDO.setStatus("2");
            orderDO.setPrice(paySaPiBO.getReallyPrice());
            orderDO.setPayTime(new Date());
            orderMapper.update(orderDO, new QueryWrapper<OrderDO>().eq("order_id", payId));
            OrderDO order = orderMapper.selectOne(new QueryWrapper<OrderDO>().eq("order_id", payId));
            //修改用户金币数据
            UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
            userUpdateDTO.setId(order.getUserId());
            if (order.getIsMember().equals(Constant.MEMBER)) {
                userUpdateDTO.setIsMember(Constant.MEMBER);
            } else {
                //查询原有用户金币
                UserByIdVO user = userFeign.getUser(new GetUserDTO(order.getUserId(), ""));
                userUpdateDTO.setPoint(user.getPoint() + order.getPoint());
            }
            userFeign.update(userUpdateDTO);
            return "success";
        }
        return "error";
    }
}
