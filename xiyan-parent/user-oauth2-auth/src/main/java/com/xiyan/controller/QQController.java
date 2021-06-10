package com.xiyan.controller;

import com.alibaba.fastjson.JSONArray;
import com.xiyan.service.QQService;
import com.xiyan.util.AESUtil;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.QQRequestVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * @author : bright
 * @Description :
 * @date:Created in 2021/1/29 0029 16:25
 */
@Controller
@RequestMapping("/qq")
public class QQController {
    @Resource
    private QQService qqService;

    @Resource
    private HttpServletRequest request;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Value("${web.url}")
    private String webUrl;

    @ApiOperation(value = "QQ回调接口")
    @GetMapping("/back")
    public String QQBack() throws Exception {
        BaseVO baseVO = qqService.QQBack(request.getParameter("code"), request.getParameter("state"));
        //获取密钥
        String keypair = redisTemplate.opsForValue().get("aes-keypair");
        String q = AESUtil.aesEncrypt(JSONArray.toJSONString(baseVO), keypair);
        return "redirect:" + webUrl + "?param=" + q;
    }

    @ApiOperation(value = "QQ登陆请求接口")
    @GetMapping("/login")
    @ResponseBody
    public QQRequestVO login() throws UnsupportedEncodingException {
        return qqService.login();
    }
}
