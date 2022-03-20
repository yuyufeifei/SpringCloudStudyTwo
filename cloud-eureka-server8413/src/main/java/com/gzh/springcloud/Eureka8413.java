package com.gzh.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author GZH
 * @date 2022-03-04
 */
@SpringBootApplication
@EnableEurekaServer
public class Eureka8413 {
    public static void main(String[] args) {
        SpringApplication.run(Eureka8413.class, args);
    }
}
