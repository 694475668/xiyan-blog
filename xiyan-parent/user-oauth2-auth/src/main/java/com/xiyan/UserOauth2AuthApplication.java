package com.xiyan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author bright
 */
@SpringBootApplication
@EnableDiscoveryClient
public class UserOauth2AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserOauth2AuthApplication.class, args);
    }
}
