package com.gzh.springcloud.filter;

import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * @author GZH
 * @date 2022-03-11
 *
 * 测试：访问http://localhost:8448/payment/2?username=11     带了username参数才能访问
 */
//@Configuration
@Log4j2
public class GatewayFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("come in GatewayFilter:" + new Date());
        String username = exchange.getRequest().getQueryParams().getFirst("username");
        if (username == null) {
            log.info("用户名为空，非法。");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
