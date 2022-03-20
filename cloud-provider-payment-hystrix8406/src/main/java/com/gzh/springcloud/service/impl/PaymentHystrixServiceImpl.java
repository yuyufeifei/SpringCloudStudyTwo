package com.gzh.springcloud.service.impl;

import com.gzh.springcloud.service.PaymentHystrixService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author GZH
 * @date 2022-03-08
 */
@Service
@Log4j2
public class PaymentHystrixServiceImpl implements PaymentHystrixService {

    @Override
    public String paymentInfoOK(Integer id) {
        return "线程：" + Thread.currentThread().getName() + " paymentInfo_OK,id:" + id;
    }

    @Override
    public String paymentInfoTimeout(Integer id) {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {
            log.error(e);
        }
        return "线程：" + Thread.currentThread().getName() + " paymentInfo_Timeout,id:" + id;
    }

}
