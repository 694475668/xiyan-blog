package com.xiyan.config;


import com.alibaba.cloud.sentinel.SentinelProperties;
import com.alibaba.cloud.sentinel.datasource.config.NacosDataSourceProperties;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.Set;

/**
 * @Description: 网关限流规则持久化
 * @author: bright
 * @Date: 2020/9/25 13:46
 */
@Configuration
@Order(2)
public class SentinelPersistenceConfig {

    @Autowired
    private SentinelProperties sentinelProperties;

    @Bean
    public SentinelPersistenceConfig init() throws Exception {
        loadGWFlowRule();
        return new SentinelPersistenceConfig();
    }

    private void loadGWFlowRule() {
        sentinelProperties.getDatasource().entrySet().stream().filter(map -> {
            return map.getValue().getNacos() != null;
        }).forEach(map -> {
            NacosDataSourceProperties dataSource = map.getValue().getNacos();
            ReadableDataSource<String, Set<GatewayFlowRule>> gwFlowRuleDataSource = new NacosDataSource<>(
                    dataSource.getServerAddr(), dataSource.getGroupId(), dataSource.getDataId(),
                    source -> JSON.parseObject(source, new TypeReference<Set<GatewayFlowRule>>() {
                    }));
            GatewayRuleManager.register2Property(gwFlowRuleDataSource.getProperty());
        });
    }
}