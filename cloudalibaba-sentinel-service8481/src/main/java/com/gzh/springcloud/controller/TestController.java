package com.gzh.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author GZH
 * @date 2022-03-17
 *
 * 链路流控模式指的是，当从某个接口过来的资源达到限流条件时，开启限流
 * 具体可参考：https://blog.csdn.net/qq_31155349/article/details/108478190
 * 默认提示为：Blocked by Sentinel (flow limiting)
 */
@RestController
@Log4j2
public class TestController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("test1")
    public Object test1() {
        //sleep可模拟流控规则中的线程数
        try {
            TimeUnit.MILLISECONDS.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "test1--" + serverPort;
    }

    @GetMapping("test2")
    public Object test2() {
        log.info(Thread.currentThread().getName() + ", test2, " + serverPort);
        return "test2--" + serverPort;
    }

    @GetMapping("test3")
    public Object test3() {
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "test3--" + serverPort;
    }

    /**
     * 热点规则按参数索引匹配，如配置了第0个参数，则访问“http://localhost:8481/testHotKey?p1=1”时超过QPS值会返回"hot key block"
     */
    @GetMapping("testHotKey")
    @SentinelResource(value = "testHotKey", blockHandler = "testHotKeyBlock")
    public Object testHotKey(@RequestParam(value = "p1", required = false) String p1,
                             @RequestParam(value = "p2", required = false) String p2) {
        return "hotkey";
    }
    public Object testHotKeyBlock(String p1, String p2, BlockException be) {
        log.error(be);
        return "hot key block";
    }

}
