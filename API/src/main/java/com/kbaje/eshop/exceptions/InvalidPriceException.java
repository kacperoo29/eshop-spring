package com.kbaje.eshop.exceptions;

import java.math.BigDecimal;

public class InvalidPriceException extends RuntimeException {
    
    public InvalidPriceException(BigDecimal price) {
        super("Invalid price: " + price);
    }
}
