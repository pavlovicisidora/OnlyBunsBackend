package com.ISA.OnlyBunsBackend.mapper;

import com.ISA.OnlyBunsBackend.dto.UserRegistration;
import com.ISA.OnlyBunsBackend.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper {
    private static ModelMapper modelMapper;

    @Autowired
    public UserDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static User fromDTOtoUser(UserRegistration dto) {
        return modelMapper.map(dto, User.class);
    }

    public static UserRegistration fromUserToDTO(User user) {
        return modelMapper.map(user, UserRegistration.class);
    }
}
