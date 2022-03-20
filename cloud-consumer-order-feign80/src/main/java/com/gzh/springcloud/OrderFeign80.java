package com.gzh.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author GZH
 * @date 2022-03-08
 */
@SpringBootApplication
@EnableFeignClients
public class OrderFeign80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderFeign80.class, args);
    }
}
