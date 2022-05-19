package com.kbaje.eshop.services;

import com.kbaje.eshop.dto.CreateUserDto;
import com.kbaje.eshop.dto.UserDto;
import com.kbaje.eshop.mapping.UserMapper;
import com.kbaje.eshop.models.User;
import com.kbaje.eshop.services.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserDto createUser(CreateUserDto payload) {
        User user = User.Create(payload.email, payload.password);
        User createdUser = userRepository.save(user);
        
        return userMapper.mapToDto(createdUser);
    }
}
