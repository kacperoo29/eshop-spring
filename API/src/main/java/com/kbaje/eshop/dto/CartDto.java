package com.kbaje.eshop.dto;

import java.util.List;
import java.util.UUID;

import com.kbaje.eshop.models.CartState;

public class CartDto {
    
    public UUID id;
    public List<CartProductDto> products;
    public CartState state;

}
