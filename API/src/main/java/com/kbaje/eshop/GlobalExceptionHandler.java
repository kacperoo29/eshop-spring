package com.kbaje.eshop;

import com.kbaje.eshop.exceptions.EmptyFieldException;
import com.kbaje.eshop.exceptions.EntityNotFoundException;
import com.kbaje.eshop.exceptions.IllegalCartStateException;
import com.kbaje.eshop.exceptions.InvalidEmailException;
import com.kbaje.eshop.exceptions.InvalidPriceException;
import com.kbaje.eshop.exceptions.ProductReferencedException;
import com.kbaje.eshop.exceptions.UserAlreadyExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({
            UserAlreadyExistsException.class,
            InvalidEmailException.class,
            IllegalCartStateException.class,
            InvalidPriceException.class,
            EmptyFieldException.class,
            ProductReferencedException.class
    })
    public ResponseEntity<Object> handleUserException(RuntimeException ex) {
        logger.error("Exception: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler({
        EntityNotFoundException.class
    })
    public ResponseEntity<Object> handleEntityNotFoundException(RuntimeException ex) {
        logger.error("Exception: {}", ex.getMessage());
        return ResponseEntity.notFound().build();
    }
    
}
