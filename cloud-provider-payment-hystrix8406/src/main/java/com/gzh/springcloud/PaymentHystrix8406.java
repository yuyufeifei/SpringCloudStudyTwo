package com.gzh.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * @author GZH
 * @date 2022-03-08
 *
 * 注解@EnableCircuitBreaker新版本已弃用，改用@EnableHystrix
 * 注解@EnableHystrix中包含了注解@EnableCircuitBreaker
 * 注解@EnableHystrix为使用Hystrix实现服务熔断？降级？
 */
@SpringBootApplication
@EnableHystrix
public class PaymentHystrix8406 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentHystrix8406.class, args);
    }
}
