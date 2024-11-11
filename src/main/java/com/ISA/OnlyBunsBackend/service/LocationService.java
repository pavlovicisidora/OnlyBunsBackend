package com.ISA.OnlyBunsBackend.service;

import com.ISA.OnlyBunsBackend.model.Location;

import java.util.List;

public interface LocationService {
    Location findById(int id);
    List<Location> findAll ();
}
