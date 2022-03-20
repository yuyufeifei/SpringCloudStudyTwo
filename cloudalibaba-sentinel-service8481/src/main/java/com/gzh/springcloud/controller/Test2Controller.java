package com.gzh.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.gzh.springcloud.handler.MyBlockHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GZH
 * @date 2022-03-17
 */
@RestController
@Log4j2
public class Test2Controller {
    @GetMapping("test21/a")
    @SentinelResource(value = "test21", blockHandler = "testBlock")
    public Object test21() {
        return "test21";
    }
    public Object testBlock(BlockException be) {
        log.error(be);
        return "test21 block";
    }

    @GetMapping("test22/b")
    @SentinelResource(value = "test22", blockHandlerClass = {MyBlockHandler.class}, blockHandler = "handlerException1")
    public Object test22() {
        return "test22 b";
    }
}
