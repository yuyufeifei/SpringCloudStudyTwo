package com.gzh.springcloud.controller;

import com.gzh.springcloud.entities.Payment;
import com.gzh.springcloud.entities.Result;
import com.gzh.springcloud.service.PaymentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

/**
 * @author GZH
 * @date 2022-03-03
 */
@Log4j2
@RestController
public class PaymentController {

    private final PaymentService paymentService;
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/payment/{id}")
    public Object getPaymentById (@PathVariable Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("查询的结果为：" + payment);
        if (payment != null) {
            return new Result<Object>(200, "8403成功", payment);
        } else {
            return new Result<>(400, "8403失败");
        }
    }

    @PostMapping("/payment")
    public Object addPayment(@RequestBody Payment payment) {
        log.info("接收的数据为：" + payment);
        int result = paymentService.addPayment(payment);
        log.info("添加的结果为：" + result);
        if (result > 0) {
            return new Result<Object>(200, "8403成功", result);
        } else {
            return new Result<>(400, "8403失败");
        }
    }

}
