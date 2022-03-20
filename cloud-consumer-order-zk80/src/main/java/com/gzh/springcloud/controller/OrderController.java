package com.gzh.springcloud.controller;

import com.gzh.springcloud.entities.Payment;
import com.gzh.springcloud.entities.Result;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author GZH
 * @date 2022-03-03
 */
@Log4j2
@RestController
public class OrderController {

    private final RestTemplate restTemplate;
    public OrderController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public static final String PAYMENT_URL = "http://provider-payment-zk";

    @GetMapping("/consumer/payment")
    public Object payment() {
        return restTemplate.getForObject(PAYMENT_URL + "/payment", String.class);
    }

}
