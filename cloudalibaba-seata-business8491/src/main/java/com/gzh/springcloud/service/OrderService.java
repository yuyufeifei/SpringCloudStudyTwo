package com.gzh.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author GZH
 * @date 2022-03-18
 */
@FeignClient("seata-order-service")
public interface OrderService {
    @PostMapping(path = "/order")
    String order(@RequestParam("userId") String userId,
                 @RequestParam("commodityCode") String commodityCode,
                 @RequestParam("orderCount") int orderCount);
}
