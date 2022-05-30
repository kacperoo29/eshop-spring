package com.kbaje.eshop.dto;

import java.util.UUID;

public class AddProductToCartDto {

    public UUID productId;
    public int quantity;

    public AddProductToCartDto(UUID productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

}
