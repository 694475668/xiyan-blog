package com.xiyan.config;

import com.xiyan.constants.Constant;
import com.xiyan.util.AESUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 初始化配置
 *
 * @author bright
 */
@Configuration
@RefreshScope
public class InitConfig {

    @Value("${pay.app_secret}")
    private String appSecret;

    @Value("${pay.url}")
    private String url;

    @Bean
    public void init() {
        //生成aes私钥对
        Constant.AES_KEYPAIR = AESUtil.genEncodeRule();

        Constant.APP_SECRET = appSecret;
        Constant.URL = url;
    }
}