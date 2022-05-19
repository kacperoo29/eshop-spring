package com.kbaje.eshop.mapping;

import com.kbaje.eshop.dto.CartDto;
import com.kbaje.eshop.models.Cart;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CartMapper {
    
    CartMapper Instance = Mappers.getMapper(CartMapper.class);

    CartDto mapToDto(Cart cart);

    Iterable<CartDto> mapMultipleToDto(Iterable<Cart> carts);
}
