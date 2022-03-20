package com.gzh.springcloud.service;

/**
 * @author GZH
 * @date 2022-03-08
 */
public interface PaymentHystrixService {

    String paymentInfoOK(Integer id);

    String paymentInfoTimeout(Integer id);

}
