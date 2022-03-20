package com.gzh.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author GZH
 * @date 2022-03-04
 */
@SpringBootApplication
@EnableDiscoveryClient
public class PaymentZK8404 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentZK8404.class, args);
    }
}
