package com.ISA.OnlyBunsBackend.controller;

import com.ISA.OnlyBunsBackend.model.RabbitCareLocation;
import com.ISA.OnlyBunsBackend.service.RabbitCareLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class RabbitCareLocationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RabbitCareLocationService rabbitCareLocationService;

    @GetMapping("/rabbitCareLocation/all")
    //@PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("isAuthenticated()")
    public List<RabbitCareLocation> loadAll() {
        return this.rabbitCareLocationService.findAll();
    }
}