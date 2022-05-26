package com.kbaje.eshop.exceptions;

import java.util.UUID;

public class EntityNotFoundException extends RuntimeException {
    
    public EntityNotFoundException(Class<?> clazz, UUID id) {
        super(String.format("%s with id %s not found", clazz.getSimpleName(), id.toString()));
    }
}
