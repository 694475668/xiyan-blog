package com.xiyan.controller;

import com.xiyan.bo.PaySaPiBO;
import com.xiyan.service.PayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * @author : bright
 * @Description :
 * @date:Created in 20211/30 0030 11:02
 */
@Api(tags = "支付集合")
@RestController
@RequestMapping("/pay")
public class PayController {

    @Resource
    private PayService payService;

    @ApiOperation(value = "回调接口")
    @GetMapping("/back")
    public String back(PaySaPiBO paySaPiBO) throws UnsupportedEncodingException {
        return payService.back(paySaPiBO);
    }
}
