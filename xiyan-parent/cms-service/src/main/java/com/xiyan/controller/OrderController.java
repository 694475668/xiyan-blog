package com.xiyan.controller;


import com.xiyan.dto.OrderDTO;
import com.xiyan.service.OrderService;
import com.xiyan.vo.BaseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

/**
 * @version :
 * @author: bright
 * @date:Created in 2020/7/9 13:36
 */
@Api(tags = "订单集合")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private HttpServletRequest request;

    @Resource
    private OrderService orderService;

    @PostMapping("/create")
    @ApiOperation(value = "创建订单接口")
    public BaseVO create(@RequestBody @Valid OrderDTO orderDTO) throws UnsupportedEncodingException {
        return orderService.create(orderDTO, Integer.valueOf(request.getHeader("User-ID")));
    }
}
