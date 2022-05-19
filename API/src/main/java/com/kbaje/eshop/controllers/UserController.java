package com.kbaje.eshop.controllers;

import com.kbaje.eshop.dto.AuthDto;
import com.kbaje.eshop.dto.AuthRequestDto;
import com.kbaje.eshop.dto.CreateUserDto;
import com.kbaje.eshop.dto.UserDto;
import com.kbaje.eshop.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/checkout")
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping("authenticate")
    public AuthDto authenticate(@RequestBody AuthRequestDto payload) {
        return new AuthDto(false, "");
    }

    @PostMapping("createUser")
    public UserDto createUser(@RequestBody CreateUserDto payload) {
        return userService.createUser(payload);
    }

}
