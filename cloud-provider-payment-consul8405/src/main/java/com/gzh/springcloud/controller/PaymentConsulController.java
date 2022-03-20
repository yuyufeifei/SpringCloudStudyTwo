package com.gzh.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author GZH
 * @date 2022-03-07
 */
@RestController
public class PaymentConsulController {
    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/payment")
    public String paymentConsul() {
        return "spring cloud consul:" + serverPort + "\t" + UUID.randomUUID();
    }
}
