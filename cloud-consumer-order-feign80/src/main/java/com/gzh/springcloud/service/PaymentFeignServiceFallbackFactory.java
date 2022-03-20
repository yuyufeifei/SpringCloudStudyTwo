package com.gzh.springcloud.service;

import com.gzh.springcloud.entities.Payment;
import com.gzh.springcloud.entities.Result;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author GZH
 * @date 2022-03-08
 */
@Component
public class PaymentFeignServiceFallbackFactory implements FallbackFactory<PaymentFeignService> {
    @Override
    public PaymentFeignService create(Throwable cause) {
        return new PaymentFeignService() {

            /**
             * 8401
             */
            @Override
            public Object getPaymentById(Long id) {
                return new Result<>(6000, "前方拥挤。");
            }

            @Override
            public Object addPayment(Payment payment) {
                return new Result<>(6001, "前方太拥挤。");
            }

            @Override
            public Object paymentFeignTimeout() {
                return new Result<>(6002, "超时。");
            }

        };
    }
}
