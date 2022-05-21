package com.kbaje.eshop.dto;

public class CreateUserDto {
    
    public String username;
    public String email;
    public String password;

    public CreateUserDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

}
