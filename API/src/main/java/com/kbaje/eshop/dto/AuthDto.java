package com.kbaje.eshop.dto;

public class AuthDto {

    public Boolean isSucessfull;
    public String token;

    public AuthDto(Boolean isSucessfull, String token) {
        this.isSucessfull = isSucessfull;
        this.token = token;
    }

}
