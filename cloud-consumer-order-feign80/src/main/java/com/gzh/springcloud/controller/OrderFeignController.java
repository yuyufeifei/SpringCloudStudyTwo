package com.gzh.springcloud.controller;

import com.gzh.springcloud.entities.Payment;
import com.gzh.springcloud.service.PaymentFeignService;
import com.gzh.springcloud.service.PaymentFeignHystrixService;
import org.springframework.web.bind.annotation.*;

/**
 * @author GZH
 * @date 2022-03-08
 */
@RestController
public class OrderFeignController {

    private final PaymentFeignService paymentFeignService;
    private final PaymentFeignHystrixService paymentFeignHystrixService;
    public OrderFeignController(PaymentFeignService paymentFeignService, PaymentFeignHystrixService paymentFeignHystrixService) {
        this.paymentFeignService = paymentFeignService;
        this.paymentFeignHystrixService = paymentFeignHystrixService;
    }

    @GetMapping("/consumer/payment/{id}")
    public Object get(@PathVariable Long id) {
        return paymentFeignService.getPaymentById(id);
    }

    @PostMapping("/consumer/payment")
    public Object add(@RequestBody Payment payment) {
        return paymentFeignService.addPayment(payment);
    }

    @GetMapping("/consumer/payment/feign/timeout")
    public Object feignTimeout() {
        return paymentFeignService.paymentFeignTimeout();
    }

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public Object hystrixOK(@PathVariable Integer id) {
        return paymentFeignHystrixService.paymentHystrixOK(id);
    }

    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    public Object hystrixTimeout(@PathVariable Integer id) {
        return paymentFeignHystrixService.paymentHystrixTimeout(id);
    }

}
