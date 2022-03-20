package com.gzh.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author GZH
 * @date 2022-03-17
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SentinelService8481 {
    public static void main(String[] args) {
        SpringApplication.run(SentinelService8481.class, args);
    }
}
