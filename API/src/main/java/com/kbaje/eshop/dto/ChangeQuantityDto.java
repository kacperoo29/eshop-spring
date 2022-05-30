package com.kbaje.eshop.dto;

import java.util.UUID;

public class ChangeQuantityDto {
    
    public UUID productId;
    public int quantity;

    public ChangeQuantityDto(UUID productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
    
}
