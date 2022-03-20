package com.gzh.springcloud.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author GZH
 * @date 2022-03-07
 */
@RestController
@Log4j2
public class OrderController {
    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;
    public OrderController(RestTemplate restTemplate, DiscoveryClient discoveryClient) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    public static final String PAYMENT_URL = "http://provider-payment-consul";

    @GetMapping("/consumer/payment")
    public Object payment() {
        serviceUrl();
        String result = restTemplate.getForObject(PAYMENT_URL + "/payment", String.class);
        log.info(result);
        return result;
    }

    public void serviceUrl() {
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            log.info("element:" + element);
            List<ServiceInstance> instances = discoveryClient.getInstances(element);
            for (ServiceInstance instance : instances) {
                log.info(instance.getServiceId());
                log.info(instance.getInstanceId());
                log.info(instance.getHost());
                log.info(instance.getPort());
                log.info(instance.getUri());
                log.info(instance.getScheme());
                log.info(instance.getMetadata());
            }
        }
    }

}
