package com.gzh.springcloud.controller;

import com.gzh.springcloud.entities.Payment;
import com.gzh.springcloud.entities.Result;
import com.gzh.springcloud.service.PaymentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author GZH
 * @date 2022-03-03
 */
@Log4j2
@RestController
public class PaymentController {

    private final PaymentService paymentService;
    private final DiscoveryClient discoveryClient;
    public PaymentController(PaymentService paymentService, DiscoveryClient discoveryClient) {
        this.paymentService = paymentService;
        this.discoveryClient = discoveryClient;
    }

    @GetMapping("/payment/{id}")
    public Object getPaymentById(@PathVariable Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("查询的结果为：" + payment);
        if (payment != null) {
            return new Result<Object>(200, "8401成功", payment);
        } else {
            return new Result<>(400, "8401失败");
        }
    }

    @PostMapping("/payment")
    public Object addPayment(@RequestBody Payment payment) {
        log.info("接收的数据为：" + payment);
        int result = paymentService.addPayment(payment);
        log.info("添加的结果为：" + result);
        if (result > 0) {
            return new Result<Object>(200, "8401成功", result);
        } else {
            return new Result<>(400, "8401失败");
        }
    }

    @GetMapping("/payment/discovery")
    public Object discovery() {
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            log.info("element:" + element);
            List<ServiceInstance> instances = discoveryClient.getInstances(element);
            for (ServiceInstance instance : instances) {
                log.info(instance.getServiceId());
                log.info(instance.getInstanceId());
                log.info(instance.getHost());
                log.info(instance.getPort());
                log.info(instance.getUri());
                log.info(instance.getScheme());
                log.info(instance.getMetadata());
            }
        }
        return discoveryClient;
    }
    //结果
    /*
     * element:provider-payment
     * PROVIDER-PAYMENT
     * provider-payment:8402
     * 192.168.137.1
     * 8402
     * http://192.168.137.1:8402
     * http
     * {management.port=8402}
     */

    @GetMapping("/payment/feign/timeout")
    public Object paymentFeignTimeout() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {
            log.error(e);
        }
        return "8401";
    }

}
