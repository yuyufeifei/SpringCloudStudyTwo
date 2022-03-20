package com.gzh.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author GZH
 * @date 2022-03-09
 */
@Component
@FeignClient(value = "PROVIDER-PAYMENT-HYSTRIX", fallbackFactory = PaymentFeignHystrixServiceFallbackFactory.class)
public interface PaymentFeignHystrixService {

    /**
     * 以下调用8406的服务
     */
    @GetMapping("/payment/hystrix/ok/{id}")
    Object paymentHystrixOK(@PathVariable("id") Integer id);

    @GetMapping("/payment/hystrix/timeout/{id}")
    Object paymentHystrixTimeout(@PathVariable("id") Integer id);

}
