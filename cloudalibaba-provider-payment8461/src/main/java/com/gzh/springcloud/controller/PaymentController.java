package com.gzh.springcloud.controller;

import com.gzh.springcloud.entities.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GZH
 * @date 2022-03-16
 */
@RestController
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/payment/{id}")
    public Object getPayment(@PathVariable Integer id) {
        return new Result<>(200, "成功！", "serverPort:" + serverPort + ",id:" + id);
    }

}
