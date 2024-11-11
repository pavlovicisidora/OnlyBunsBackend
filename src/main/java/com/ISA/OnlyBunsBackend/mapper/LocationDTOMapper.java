package com.ISA.OnlyBunsBackend.mapper;

import com.ISA.OnlyBunsBackend.dto.LocationDTO;
import com.ISA.OnlyBunsBackend.dto.UserRegistration;
import com.ISA.OnlyBunsBackend.model.Location;
import com.ISA.OnlyBunsBackend.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationDTOMapper {

    private static ModelMapper modelMapper;

    @Autowired
    public LocationDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static Location fromDTOtoUser(LocationDTO dto) {
        return modelMapper.map(dto, Location.class);
    }

    public static LocationDTO fromUserToDTO(Location location) {
        return modelMapper.map(location, LocationDTO.class);
    }
}
