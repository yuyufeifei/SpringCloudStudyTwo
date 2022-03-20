package com.gzh.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author GZH
 * @date 2022-03-03
 *
 * 此注解不加也可 @EnableEurekaClient
 */
@SpringBootApplication
public class Payment8401 {
    public static void main(String[] args) {
        SpringApplication.run(Payment8401.class, args);
    }
}
