package com.kbaje.eshop.mapping;

import com.kbaje.eshop.dto.CartDto;
import com.kbaje.eshop.dto.CartProductDto;
import com.kbaje.eshop.dto.ProductDto;
import com.kbaje.eshop.dto.UserDto;
import com.kbaje.eshop.models.AppUser;
import com.kbaje.eshop.models.Cart;
import com.kbaje.eshop.models.CartProduct;
import com.kbaje.eshop.models.Product;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MapperProfile {
    
    MapperProfile Instance = Mappers.getMapper(MapperProfile.class);

    ProductDto productToDto(Product product);

    Iterable<ProductDto> productsToDto(Iterable<Product> products);

    CartProductDto cartProductToDto(CartProduct cartProduct);
    Iterable<CartProductDto> cartProductsToDto(Iterable<CartProduct> cartProducts);

    CartDto cartToDto(Cart cart);
    Iterable<CartDto> cartsToDto(Iterable<Cart> carts);

    UserDto userToDto(AppUser user);
    Iterable<UserDto> usersToDto(Iterable<AppUser> users);
}
