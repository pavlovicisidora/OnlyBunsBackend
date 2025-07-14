package com.ISA.OnlyBunsBackend.controller;


import com.ISA.OnlyBunsBackend.dto.RabbitCareLocationDTO;
import com.ISA.OnlyBunsBackend.model.RabbitCareLocation;
import com.ISA.OnlyBunsBackend.util.Producer;
import com.ISA.OnlyBunsBackend.service.RabbitCareLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api")
public class ProducerController {

    @Autowired
    private Producer producer;
    @Autowired
    private RabbitCareLocationService rabbitCareLocationService;

    @PostMapping(value="/{queue}", consumes = "application/json")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> sendMessage(@PathVariable("queue") String queue, @RequestBody RabbitCareLocationDTO message) {
        //rabbitCareLocationService.save(message);
        producer.sendTo(queue, message);
        return ResponseEntity.ok().build();
    }

}