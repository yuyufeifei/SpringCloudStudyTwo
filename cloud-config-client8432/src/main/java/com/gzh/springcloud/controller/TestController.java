package com.gzh.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GZH
 * @date 2022-03-11
 *
 * 自动刷新配置需添加@RefreshScope
 * 在gitee修改配置之后，需要用curl命令发送 curl -X POST "http://localhost:8432/actuator/refresh"
 * 然后配置就更新了
 */
@RestController
@RefreshScope
public class TestController {
    @Value("${config.info}")
    private String configInfo;

    @GetMapping("config")
    public String getConfigInfo() {
        return configInfo;
    }

}
