package com.kbaje.eshop;

import com.kbaje.eshop.exceptions.EmptyFieldException;
import com.kbaje.eshop.exceptions.IllegalCartStateException;
import com.kbaje.eshop.exceptions.InvalidEmailException;
import com.kbaje.eshop.exceptions.InvalidPriceException;
import com.kbaje.eshop.exceptions.UserAlreadyExistsException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            UserAlreadyExistsException.class,
            InvalidEmailException.class,
            IllegalCartStateException.class,
            InvalidPriceException.class,
            EmptyFieldException.class
    })
    public ResponseEntity<Object> handleUserException(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
