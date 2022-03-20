package com.gzh.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author GZH
 * @date 2022-03-16
 */
@RestController
@Log4j2
public class OrderController {

    private final RestTemplate restTemplate;

    public OrderController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${service-url.nacos-user-service}")
    private String serviceUrl;

    @GetMapping("/consumer/payment/{id}")
    @SentinelResource(value = "payment", fallback = "paymentFallback", blockHandler = "paymentBlockHandler", exceptionsToIgnore = {IllegalArgumentException.class})
    public Object getPayment(@PathVariable Integer id) {
        if (id == 0) {
            throw new IllegalArgumentException("参数不能为0");
        }
        return restTemplate.getForObject(serviceUrl + "/payment/" + id, String.class);
    }

    /**
     * 参数 Throwable t 可选
     */
    public Object paymentFallback(@PathVariable Integer id, Throwable t) {
        return "payment fallback";
    }

    public Object paymentBlockHandler(Integer id, BlockException be) {
        log.error(be);
        return "payment block handler";
    }

}
