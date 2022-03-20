package com.gzh.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author GZH
 * @date 2022-03-18
 */
@FeignClient("seata-storage-service")
public interface StorageService {
    @GetMapping(path = "/storage/{commodityCode}/{count}")
    String storage(@PathVariable("commodityCode") String commodityCode,
                   @PathVariable("count") int count);
}
