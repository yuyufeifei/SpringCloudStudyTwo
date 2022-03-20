package com.gzh.springcloud.controller;

import com.gzh.springcloud.entities.Result;
import com.gzh.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author GZH
 * @date 2022-03-08
 *
 * 注：@HystrixCommand和fallback方法写在service层？
 */
@RestController
@Log4j2
@DefaultProperties(defaultFallback = "globalHystrixFallback")
public class PaymentHystrixController {

    private final PaymentHystrixService paymentHystrixService;
    public PaymentHystrixController(PaymentHystrixService paymentHystrixService) {
        this.paymentHystrixService = paymentHystrixService;
    }

    @GetMapping("/payment/hystrix/ok/{id}")
    public Object paymentHystrixOK(@PathVariable Integer id) {
        String result = paymentHystrixService.paymentInfoOK(id);
        log.info(result);
        return new Result<String>(200, "成功", result);
    }

    /**
     * 业务执行需要3秒，Hystrix设置1.5秒后执行fallback方法
     */
    @GetMapping("/payment/hystrix/timeout/{id}")
    @HystrixCommand(fallbackMethod = "paymentHystrixTimeoutFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")
    })
    public Object paymentHystrixTimeout(@PathVariable Integer id) {
        String result = paymentHystrixService.paymentInfoTimeout(id);
        log.info(result);
        return new Result<String>(201, "调用成功", result);
    }
    /**
     * 注意：此方法的参数需和调用方一致
     */
    public Object paymentHystrixTimeoutFallback(@PathVariable Integer id) {
        String data = "线程：" + Thread.currentThread().getName() + "调用8406的Hystrix Fallback";
        return new Result<String>(201, "调用成功", data);
    }

    /**
     * 业务执行需要3秒，Hystrix设置了全局fallback方法 globalHystrixFallback
     */
    @GetMapping("/payment/hystrix/timeout2/{id}")
    @HystrixCommand
    public Object paymentHystrixTimeout2(@PathVariable Integer id) {
        String result = paymentHystrixService.paymentInfoTimeout(id);
        log.info(result);
        return new Result<String>(202, "调用成功", result);
    }

    public Object globalHystrixFallback() {
        return new Result<String>(203, "调用成功", "8406的全局Hystrix Fallback");
    }

    /**
     * 服务熔断
     * 注解@HystrixProperty中的参数在HystrixCommandProperties类中可找到
     *
     * 测试：先访问id为负数的链接，会得到fallback中的提示；
     *      失败率达到60%后，再访问id为正数的链接，还会得到fallback中的提示，过一段时间后恢复正常
     */
    @GetMapping("/payment/hystrix/cb/{id}")
    @HystrixCommand(fallbackMethod = "paymentCircuitBreakerFallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), //请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), //时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60") //失败率达到多少后跳闸
    })
    public Object paymentCircuitBreaker(@PathVariable("id") Integer id) {
        if (id < 0) {
            throw new RuntimeException("id 不能为负数");
        }
        return new Result<>(205, "调用成功", Thread.currentThread().getName() + " paymentCircuitBreaker" + UUID.randomUUID());
    }
    public Object paymentCircuitBreakerFallback(@PathVariable("id") Integer id) {
        return new Result<>(205, "调用成功", "paymentCircuitBreakerFallback - id不能为负数，id:" + id);
    }

    @HystrixCommand(fallbackMethod = "strFallbackMethod",
            groupKey = "strGroupCommand", commandKey = "strCommand", threadPoolKey = "strThreadPool",
            commandProperties = {

            @HystrixProperty(name = "", value = ""),
            @HystrixProperty(name = "", value = ""),
            @HystrixProperty(name = "", value = ""),
            @HystrixProperty(name = "", value = ""),
            @HystrixProperty(name = "", value = ""),
    })
    public Object allProperties() {
        return null;
    }

}
