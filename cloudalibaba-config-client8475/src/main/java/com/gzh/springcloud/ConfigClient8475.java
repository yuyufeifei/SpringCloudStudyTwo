package com.gzh.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author GZH
 * @date 2022-03-16
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ConfigClient8475 {
    public static void main(String[] args) {
        SpringApplication.run(ConfigClient8475.class, args);
    }
}
