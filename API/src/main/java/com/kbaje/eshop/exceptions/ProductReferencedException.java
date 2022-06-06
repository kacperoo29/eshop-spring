package com.kbaje.eshop.exceptions;

import java.util.UUID;

public class ProductReferencedException extends RuntimeException {
    
    public ProductReferencedException(UUID productId) {
        super(String.format("Product with id %s is referenced by some cart/order", productId));
    }
}
