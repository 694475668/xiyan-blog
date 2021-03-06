package com.xiyan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author bright
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class BackstageServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackstageServiceApplication.class, args);
    }
}