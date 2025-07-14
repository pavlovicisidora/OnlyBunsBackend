package com.ISA.OnlyBunsBackend.service.impl;

import com.ISA.OnlyBunsBackend.dto.RabbitCareLocationDTO;
import com.ISA.OnlyBunsBackend.repository.RabbitCareLocationRepository;
import com.ISA.OnlyBunsBackend.service.RabbitCareLocationService;
import com.ISA.OnlyBunsBackend.model.RabbitCareLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RabbitCareLocationServiceImpl implements RabbitCareLocationService {

    @Autowired
    private RabbitCareLocationRepository rabbitCareLocationRepository;
    @Override
    public RabbitCareLocation save(RabbitCareLocationDTO rabbitCareLocationDTO) {
        RabbitCareLocation rabbitCareLocation = new RabbitCareLocation();
        rabbitCareLocation.setName(rabbitCareLocationDTO.getName());
        rabbitCareLocation.setCity(rabbitCareLocationDTO.getCity());
        rabbitCareLocation.setLatitude(rabbitCareLocationDTO.getLatitude());
        rabbitCareLocation.setLongitude(rabbitCareLocationDTO.getLongitude());
        rabbitCareLocation.setCountry(rabbitCareLocationDTO.getCountry());
        return rabbitCareLocationRepository.save(rabbitCareLocation);
    }
    public List<RabbitCareLocation> findAll() throws AccessDeniedException {
        return rabbitCareLocationRepository.findAll();
    }
}