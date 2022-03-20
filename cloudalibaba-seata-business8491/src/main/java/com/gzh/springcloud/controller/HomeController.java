package com.gzh.springcloud.controller;

import com.gzh.springcloud.service.OrderService;
import com.gzh.springcloud.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GZH
 * @date 2022-03-18
 */
@RestController
@Log4j2
public class HomeController {

    private static final String SUCCESS = "SUCCESS";
    private static final String FAIL = "FAIL";
    private static final String USER_ID = "U100001";
    private static final String COMMODITY_CODE = "C00321";
    private static final int ORDER_COUNT = 2;

    private final OrderService orderService;
    private final StorageService storageService;
    public HomeController(OrderService orderService, StorageService storageService) {
        this.orderService = orderService;
        this.storageService = storageService;
    }

    @GlobalTransactional(timeoutMills = 300000, name = "spring-cloud-tx")
    @GetMapping(value = "/seata/feign", produces = "application/json")
    public String feign() {

        String result = storageService.storage(COMMODITY_CODE, ORDER_COUNT);
        if (!SUCCESS.equals(result)) {
            throw new RuntimeException();
        }

        result = orderService.order(USER_ID, COMMODITY_CODE, ORDER_COUNT);
        if (!SUCCESS.equals(result)) {
            throw new RuntimeException();
        }

        return SUCCESS;

    }
}
