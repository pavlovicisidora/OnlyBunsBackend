package com.ISA.OnlyBunsBackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class LoadBalancerConfig {
    @Value("${backend.service.urls:http://localhost:8081,http://localhost:8082}")
    private String backendServiceUrlsString;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(2))
                .setReadTimeout(Duration.ofSeconds(5))
                .build();
    }

    @Bean
    public List<String> serviceUrls() {
        return Arrays.stream(backendServiceUrlsString.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
