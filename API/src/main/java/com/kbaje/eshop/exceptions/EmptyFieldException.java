package com.kbaje.eshop.exceptions;

public class EmptyFieldException extends RuntimeException {

    public EmptyFieldException(Class<?> c, String field) {
        super("Field " + field + " in class " + c.getSimpleName() + " can't be empty");
    }

}