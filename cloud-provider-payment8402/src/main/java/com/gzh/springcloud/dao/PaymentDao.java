package com.gzh.springcloud.dao;

import com.gzh.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author GZH
 * @date 2022-03-03
 */
@Mapper
public interface PaymentDao {

    Payment getPaymentById(@Param("id") Long id);

    int addPayment(Payment payment);

}
