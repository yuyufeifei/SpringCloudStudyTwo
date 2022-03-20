package com.gzh.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @author GZH
 * @date 2022-03-10
 *
 * 测试：在监控页面访问http://localhost:8406/actuator/hystrix.stream
 * 注；需要在被监控的服务中添加bean
 *     public ServletRegistrationBean getServlet() {
 *         HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
 *         ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
 *         registrationBean.setLoadOnStartup(1);
 *         registrationBean.addUrlMappings("/actuator/hystrix.stream");//访问路径
 *         registrationBean.setName("hystrix.stream");
 *         return registrationBean;
 *     }
 */
@SpringBootApplication
@EnableHystrixDashboard
public class HystrixDashboard8421 {
    public static void main(String[] args) {
        SpringApplication.run(HystrixDashboard8421.class, args);
    }
}
