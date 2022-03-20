package com.gzh.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author GZH
 * @date 2022-03-19
 */
@FeignClient("seata-account-service")
public interface AccountService {

    @PostMapping(path = "/account")
    String account(@RequestParam("userId") String userId, @RequestParam("money") int money);

}
