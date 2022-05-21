package com.kbaje.eshop.exceptions;

public class IllegalCartStateException extends RuntimeException {
    
    public IllegalCartStateException(String message) {
        super(message);
    }
}
