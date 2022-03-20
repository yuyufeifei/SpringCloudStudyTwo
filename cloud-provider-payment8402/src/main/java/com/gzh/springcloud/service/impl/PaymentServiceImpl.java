package com.gzh.springcloud.service.impl;

import com.gzh.springcloud.dao.PaymentDao;
import com.gzh.springcloud.entities.Payment;
import com.gzh.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

/**
 * @author GZH
 * @date 2022-03-03
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentDao paymentDao;
    public PaymentServiceImpl(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }

    @Override
    public int addPayment(Payment payment) {
        return paymentDao.addPayment(payment);
    }

}
