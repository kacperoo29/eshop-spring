package com.kbaje.eshop.mapping;

import com.kbaje.eshop.dto.UserDto;
import com.kbaje.eshop.models.User;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    UserMapper Instance = Mappers.getMapper(UserMapper.class);

    UserDto mapToDto(User user);

    Iterable<UserDto> mapMultipleToDto(Iterable<User> users);
}
