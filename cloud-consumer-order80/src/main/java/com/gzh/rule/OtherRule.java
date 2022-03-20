package com.gzh.rule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author GZH
 * @date 2022-03-08
 */
@Configuration
public class OtherRule {
    @Bean
    public IRule randomRule() {
        return new RandomRule();
    }
}
