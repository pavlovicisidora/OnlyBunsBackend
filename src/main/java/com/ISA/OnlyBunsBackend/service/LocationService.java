package com.ISA.OnlyBunsBackend.service;

import com.ISA.OnlyBunsBackend.dto.LocationDTO;
import com.ISA.OnlyBunsBackend.model.Location;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface LocationService {

    //@Cacheable(cacheNames = "locationCache")
    Location findById(int id);
    List<Location> findAll ();
    LocationDTO createLocation(LocationDTO post);
}
