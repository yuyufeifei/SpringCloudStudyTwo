package com.gzh.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author GZH
 * @date 2022-03-18
 */
@SpringBootApplication
@EnableDiscoveryClient(autoRegister = false)
@EnableFeignClients
public class Business8491 {
    public static void main(String[] args) {
        SpringApplication.run(Business8491.class, args);
    }
}
