package com.ISA.OnlyBunsBackend.service;

import com.ISA.OnlyBunsBackend.dto.LocationDTO;
import com.ISA.OnlyBunsBackend.dto.PostDTO;
import com.ISA.OnlyBunsBackend.model.Location;

import java.util.List;

public interface LocationService {
    Location findById(int id);
    List<Location> findAll ();
    LocationDTO createLocation(LocationDTO post);
}
