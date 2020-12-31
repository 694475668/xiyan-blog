package com.xiyan.handler;

import com.xiyan.constants.Constant;
import com.xiyan.util.AESUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author bright
 * @Description 刷新规则
 * @date 2020年7月24日 上午9:05:45
 */
@Component
@Log4j2
public class RefreshJobHandler {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 每天凌晨3点更新下密钥
     */
    @XxlJob("refreshJobHandler")
    public ReturnT<String> refresh(String param) {
        // 重新生成aes秘钥对
        Constant.AES_KEYPAIR = AESUtil.genEncodeRule();
        log.info("重新生成aes秘钥对===={}", Constant.AES_KEYPAIR);
        redisTemplate.opsForValue().set(Constant.AES_KEY, Constant.AES_KEYPAIR);
        return ReturnT.SUCCESS;
    }
}