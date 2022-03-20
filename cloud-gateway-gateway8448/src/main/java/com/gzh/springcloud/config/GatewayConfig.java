package com.gzh.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author GZH
 * @date 2022-03-10
 *
 * 没成功
 */
//@Configuration
public class GatewayConfig {
//    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        routes.route("path_route", r -> r.path("/waiwang").uri("http://news.baidu.com/")).build();
        return routes.build();
    }
}
