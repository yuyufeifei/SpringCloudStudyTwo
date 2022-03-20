package com.gzh.springcloud.controller;

import com.gzh.springcloud.service.PaymentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GZH
 * @date 2022-03-18
 *
 * 测试feign的fallback：关闭nacos-provider-payment服务，再次访问得到“feign fallback”
 */
@RestController
@Log4j2
public class OrderFeignController {

    private PaymentService paymentService;
    public OrderFeignController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/consumer/feign/payment/{id}")
    public Object getPayment(@PathVariable Integer id) {
        return paymentService.getPayment(id);
    }

}
