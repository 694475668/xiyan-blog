package com.xiyan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author bright
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.xiyan.mapper"})
public class SystemBaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemBaseApplication.class, args);
    }

}
