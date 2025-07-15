package com.ISA.OnlyBunsBackend.service.impl;


import com.ISA.OnlyBunsBackend.service.AdvertiseService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AdvertiseServiceImpl implements AdvertiseService {

    private final AmqpTemplate amqpTemplate;

    @Autowired
    public AdvertiseServiceImpl(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void sendPostForAd(String description, String username, LocalDateTime timestamp) {
        String message = "Opis: " + description + ", Korisnik: " + username + ", Vreme: " + timestamp;
        amqpTemplate.convertAndSend("advertisingExchange", "", message);
        System.out.println("Poslata poruka: " + message);
    }




}
