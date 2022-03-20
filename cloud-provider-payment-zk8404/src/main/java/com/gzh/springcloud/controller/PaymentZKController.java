package com.gzh.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author GZH
 * @date 2022-03-04
 */
@RestController
public class PaymentZKController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/payment")
    public String paymentZK() {
        return "spring cloud zookeeper:" + serverPort + "\t" + UUID.randomUUID();
    }

}
