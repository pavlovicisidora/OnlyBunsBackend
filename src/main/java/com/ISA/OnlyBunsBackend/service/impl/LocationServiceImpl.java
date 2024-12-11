package com.ISA.OnlyBunsBackend.service.impl;

import com.ISA.OnlyBunsBackend.model.Location;
import com.ISA.OnlyBunsBackend.model.User;
import com.ISA.OnlyBunsBackend.repository.LocationRepository;
import com.ISA.OnlyBunsBackend.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {
    @Autowired
    private LocationRepository locationRepository;

    @Override
    public Location findById(int id) {
        return locationRepository.findById(id).get();
    }

    @Override
    public List<Location> findAll() throws AccessDeniedException {
        return locationRepository.findAll();
    }
}
