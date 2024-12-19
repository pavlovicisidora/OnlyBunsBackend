package com.ISA.OnlyBunsBackend.service.impl;

import com.ISA.OnlyBunsBackend.dto.LocationDTO;
import com.ISA.OnlyBunsBackend.dto.PostDTO;
import com.ISA.OnlyBunsBackend.mapper.LocationDTOMapper;
import com.ISA.OnlyBunsBackend.mapper.PostDTOMapper;
import com.ISA.OnlyBunsBackend.model.Location;
import com.ISA.OnlyBunsBackend.model.Post;
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

    @Override
    public LocationDTO createLocation(LocationDTO locDto) {
        Location loc = LocationDTOMapper.fromDTOtoLocation(locDto);
        loc = locationRepository.save(loc);
        return LocationDTOMapper.fromLocationToDTO(loc);
    }
}
