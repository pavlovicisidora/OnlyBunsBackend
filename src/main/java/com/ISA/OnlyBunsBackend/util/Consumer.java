package com.ISA.OnlyBunsBackend.util;

import org.slf4j.Logger;


import com.ISA.OnlyBunsBackend.dto.RabbitCareLocationDTO;
import com.ISA.OnlyBunsBackend.service.RabbitCareLocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Consumer {

    @Autowired
    ObjectMapper objectMapper;
    private static final Logger log = LoggerFactory.getLogger(Consumer.class);

    @Autowired
    RabbitCareLocationService rabbitCareLocationService;

    @RabbitListener(queues = "${myqueue}")
    public void handler(RabbitCareLocationDTO rabbitCareLocationDTO) {
        try {
            log.info("CONSUMER");
            log.info("Received LocationMessage: " + rabbitCareLocationDTO.getName());
            // You can now process the LocationMessageDTO (e.g., save it to the database)
            rabbitCareLocationService.save(rabbitCareLocationDTO);
        } catch (Exception e) {
            log.error("Error while processing message: ", e);
        }
    }
}