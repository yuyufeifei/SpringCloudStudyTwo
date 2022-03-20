package com.gzh.springcloud.service;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author GZH
 * @date 2022-03-09
 */
@Component
public class PaymentFeignHystrixServiceFallbackFactory implements FallbackFactory<PaymentFeignHystrixService> {

    @Override
    public PaymentFeignHystrixService create(Throwable throwable) {
        return new PaymentFeignHystrixService() {

            /**
             * 8406
             */
            @Override
            public Object paymentHystrixOK(Integer id) {
                return "feign fallback返回的";
            }

            @Override
            public Object paymentHystrixTimeout(Integer id) {
                return "feign fallback返回的。。";
            }

        };
    }
}
