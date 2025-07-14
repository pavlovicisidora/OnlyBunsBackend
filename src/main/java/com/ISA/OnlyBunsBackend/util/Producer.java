package com.ISA.OnlyBunsBackend.util;

import com.ISA.OnlyBunsBackend.dto.RabbitCareLocationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Producer {

    private static final Logger log = LoggerFactory.getLogger(Producer.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    ObjectMapper objectMapper;

    public void sendTo(String routingkey, RabbitCareLocationDTO message){
        try {
            // ObjectMapper objectMapper = new ObjectMapper();
            //  String messageJson = objectMapper.writeValueAsString(message);
            String messageJson = objectMapper.writeValueAsString(message);
            log.info("Sending> ... Message=[ " + messageJson + " ] RoutingKey=[" + routingkey + "]");
            this.rabbitTemplate.convertAndSend(routingkey, message);
        } catch (Exception e) {
            log.error("Error while sending message: ", e);
        }
    }
}
