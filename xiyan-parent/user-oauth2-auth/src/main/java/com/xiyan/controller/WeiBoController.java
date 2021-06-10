package com.xiyan.controller;

import com.alibaba.fastjson.JSONArray;
import com.xiyan.service.WeiBoService;
import com.xiyan.util.AESUtil;
import com.xiyan.vo.BaseVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author : bright
 * @Description :
 * @date:Created in 2021/1/29 0029 16:25
 */
@Controller
@RequestMapping("/weibo")
public class WeiBoController {
    @Resource
    private WeiBoService weiBoService;

    @Resource
    private HttpServletRequest request;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Value("${web.url}")
    private String webUrl;


    @ApiOperation(value = "微博回调接口")
    @GetMapping("/back")
    public String weiboBack() throws Exception {
        BaseVO baseVO = weiBoService.weiboBack(request.getParameter("code"));
        //获取密钥
        String keypair = redisTemplate.opsForValue().get("aes-keypair");
        String q = AESUtil.aesEncrypt(JSONArray.toJSONString(baseVO), keypair);
        return "redirect:" + webUrl + "?param=" + q;
    }
}
