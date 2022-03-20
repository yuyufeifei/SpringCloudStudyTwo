package com.gzh.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author GZH
 * @date 2022-03-11
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigServer8431 {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServer8431.class, args);
    }
}
