package com.kbaje.eshop.dto;

public class AuthDto {

    public Boolean isSucessfull;
    public String token;
    public String errorMessage;

    public AuthDto(Boolean isSucessfull, String token, String errorMessage) {
        this.isSucessfull = isSucessfull;
        this.token = token;
        this.errorMessage = errorMessage;
    }

}
