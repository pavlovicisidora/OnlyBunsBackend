package com.ISA.OnlyBunsBackend.config;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@ComponentScan
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final WebSocketAuthInterceptor webSocketAuthInterceptor;

    public WebSocketConfig(WebSocketAuthInterceptor webSocketAuthInterceptor) {
        this.webSocketAuthInterceptor = webSocketAuthInterceptor;
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // Destination prefix for subscribers
        config.setApplicationDestinationPrefixes("/app"); // Prefix for messages from client to server
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        System.out.println("Registering stomp endpoints");
        registry.addEndpoint("/chat").setAllowedOrigins("*")
                .addInterceptors(webSocketAuthInterceptor); // WebSocket endpoint
    }
}
