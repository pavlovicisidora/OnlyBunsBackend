package com.ISA.OnlyBunsBackend.service;

import com.ISA.OnlyBunsBackend.dto.RabbitCareLocationDTO;
import com.ISA.OnlyBunsBackend.model.RabbitCareLocation;

import java.util.List;

public interface RabbitCareLocationService {
    RabbitCareLocation save(RabbitCareLocationDTO locationMessageDTO);
    List<RabbitCareLocation> findAll ();
}