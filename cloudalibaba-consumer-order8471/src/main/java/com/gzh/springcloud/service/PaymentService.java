package com.gzh.springcloud.service;

import com.gzh.springcloud.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author GZH
 * @date 2022-03-18
 *
 * 注意：nacos-provider-payment/payment/{id}的返回值要为json格式。
 */
@FeignClient(value = "nacos-provider-payment", fallback = PaymentServiceFallback.class, configuration = FeignConfig.class)
public interface PaymentService {

    @GetMapping("/payment/{id}")
    Object getPayment(@PathVariable("id") Integer id);

}
