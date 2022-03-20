package com.gzh.springcloud;

import com.gzh.rule.OtherRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * @author GZH
 * @date 2022-03-03
 *
 * 替换默认轮询算法：
 * 1.在主启动类外另建包，添加算法，如随机
 * 2.主启动类添加@RibbonClient注解，name为服务提供者的服务名，configuration指向添加的算法
 * 3.Eureka3.1.0 没有ribbon 使用@LoadBalancerClient
 */
@SpringBootApplication
@RibbonClient(name = "PROVIDER-PAYMENT", configuration = OtherRule.class)
public class Order80 {
    public static void main(String[] args) {
        SpringApplication.run(Order80.class, args);
    }
}
