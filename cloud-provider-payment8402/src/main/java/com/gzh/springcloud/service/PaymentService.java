package com.gzh.springcloud.service;

import com.gzh.springcloud.entities.Payment;

/**
 * @author GZH
 * @date 2022-03-03
 */
public interface PaymentService {

    Payment getPaymentById(Long id);

    int addPayment(Payment payment);

}
