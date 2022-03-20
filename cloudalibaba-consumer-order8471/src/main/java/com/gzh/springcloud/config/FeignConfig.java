package com.gzh.springcloud.config;

import com.gzh.springcloud.service.PaymentServiceFallback;
import org.springframework.context.annotation.Bean;

/**
 * @author GZH
 * @date 2022-03-18
 */
public class FeignConfig {

    @Bean
    public PaymentServiceFallback paymentServiceFallback() {
        return new PaymentServiceFallback();
    }
}
