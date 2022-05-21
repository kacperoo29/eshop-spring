package com.kbaje.eshop.exceptions;

public class InvalidEmailException extends RuntimeException {
    
    public InvalidEmailException(String email) {
        super(String.format("Invalid email: %s", email));
    }
}
