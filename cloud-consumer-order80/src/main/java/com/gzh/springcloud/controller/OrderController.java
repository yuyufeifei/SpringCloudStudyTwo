package com.gzh.springcloud.controller;

import com.gzh.springcloud.entities.Payment;
import com.gzh.springcloud.entities.Result;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
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

//    public static final String PAYMENT_URL = "http://localhost:8401";
    public static final String PAYMENT_URL = "http://PROVIDER-PAYMENT";

    @GetMapping("/consumer/payment/{id}")
    public Object get(@PathVariable Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/payment/" + id, Result.class);
    }

    @GetMapping("/consumer/payment2/{id}")
    public Object get2(@PathVariable Long id) {
        ResponseEntity<Result> response = restTemplate.getForEntity(PAYMENT_URL + "/payment/" + id, Result.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            return new Result(3000, "失败");
        }
    }

    @GetMapping("/consumer/payment")
    public Object add(Payment payment) {
        log.info("接收的数据为：" + payment);
        return restTemplate.postForObject(PAYMENT_URL + "/payment", payment, Result.class);
    }

}
