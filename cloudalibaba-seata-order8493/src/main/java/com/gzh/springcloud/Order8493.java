package com.gzh.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author GZH
 * @date 2022-03-19
 */
@SpringBootApplication
@EnableFeignClients
public class Order8493 {
    public static void main(String[] args) {
        SpringApplication.run(Order8493.class, args);
    }
}
