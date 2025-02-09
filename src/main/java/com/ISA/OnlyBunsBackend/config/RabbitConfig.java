package com.ISA.OnlyBunsBackend.config;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    public FanoutExchange exchange() {
        return new FanoutExchange("advertisingExchange");
    }
}