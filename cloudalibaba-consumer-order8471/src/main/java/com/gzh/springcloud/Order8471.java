package com.gzh.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author GZH
 * @date 2022-03-16
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class Order8471 {
    public static void main(String[] args) {
        SpringApplication.run(Order8471.class, args);
    }
}
