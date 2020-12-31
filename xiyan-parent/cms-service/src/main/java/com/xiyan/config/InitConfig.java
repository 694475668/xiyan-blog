package com.xiyan.config;

import com.xiyan.constants.Constant;
import com.xiyan.util.AESUtil;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 加载配置
 *
 * @author bright
 */
@Configuration
@RefreshScope
public class InitConfig {

    @Bean
    public void init() {
        //生成aes私钥对
        Constant.AES_KEYPAIR = AESUtil.genEncodeRule();
    }
}