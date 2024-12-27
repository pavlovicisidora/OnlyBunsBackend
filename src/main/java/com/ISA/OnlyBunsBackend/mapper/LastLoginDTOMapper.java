package com.ISA.OnlyBunsBackend.mapper;

import com.ISA.OnlyBunsBackend.dto.LastLoginDTO;
import com.ISA.OnlyBunsBackend.model.LastLogin;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LastLoginDTOMapper {
    private static ModelMapper modelMapper;

    @Autowired
    public LastLoginDTOMapper(ModelMapper modelMapper) {this.modelMapper = modelMapper;}

    public static LastLogin fromDTOtoLastLogin(LastLoginDTO dto) {return modelMapper.map(dto, LastLogin.class);}

    public static LastLoginDTO fromLastLoginToDTO(LastLogin login) {return modelMapper.map(login, LastLoginDTO.class);}


}
