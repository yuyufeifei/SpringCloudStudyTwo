package com.gzh.springcloud.service;

import com.gzh.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author GZH
 * @date 2022-03-08
 */
@Component
@FeignClient(value = "PROVIDER-PAYMENT", fallbackFactory = PaymentFeignServiceFallbackFactory.class)
public interface PaymentFeignService {

    /**
     * 注意：此处@PathVariable后必须加("id")，否则报错
     *
     * 以下调用8401的服务
     */
    @GetMapping("/payment/{id}")
    Object getPaymentById(@PathVariable("id") Long id);

    @PostMapping("/payment")
    Object addPayment(@RequestBody Payment payment);

    @GetMapping("/payment/feign/timeout")
    Object paymentFeignTimeout();

}
