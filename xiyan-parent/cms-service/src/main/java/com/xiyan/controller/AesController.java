package com.xiyan.controller;

import com.xiyan.constants.Constant;
import com.xiyan.vo.AesVO;
import com.xiyan.vo.BaseVO;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @version :
 * @author: bright
 * @date:Created in 2020/7/9 13:36
 */
@Api(tags = "AES密钥集合")
@RestController
@Log4j2
public class AesController {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @PostMapping("/aes/key")
    public BaseVO getKey() {
        AesVO aesVO = new AesVO();
        String aesKeypair = Constant.AES_KEYPAIR;
        //设置密钥到redis中
        redisTemplate.opsForValue().set(Constant.AES_KEY, aesKeypair);
        //获取密钥
        String keypair = redisTemplate.opsForValue().get(Constant.AES_KEY);
        log.info("写入redis-key [{}]", keypair);
        aesVO.setKey(keypair);
        return aesVO;
    }
}
