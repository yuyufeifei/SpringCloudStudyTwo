package com.gzh.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author GZH
 * @date 2022-03-03
 */
@SpringBootApplication
@EnableEurekaServer
public class Eureka8411 {
    public static void main(String[] args) {
        SpringApplication.run(Eureka8411.class, args);
    }
}
