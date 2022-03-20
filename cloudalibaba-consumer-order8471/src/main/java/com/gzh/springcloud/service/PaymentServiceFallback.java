package com.gzh.springcloud.service;

import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author GZH
 * @date 2022-03-18
 */
public class PaymentServiceFallback implements PaymentService {
    @Override
    public Object getPayment(Integer id) {
        return "feign fallback";
    }
}
