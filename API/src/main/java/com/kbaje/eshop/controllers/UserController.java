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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Users", description = "Operations about users")
@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userService;

    @Operation(summary = "Authenticate", description = "Authenticate")
    @PostMapping("authenticate")
    public AuthDto authenticate(@RequestBody AuthRequestDto payload) {
        return userService.authenticate(payload);
    }
    
    @Operation(summary = "Create user", description = "Create user")
    @PostMapping("createUser")
    public UserDto createUser(@RequestBody CreateUserDto payload) {
        return userService.createUser(payload);
    }

}
