package com.kbaje.eshop.mapping;

import com.kbaje.eshop.dto.CartDto;
import com.kbaje.eshop.dto.CartProductDto;
import com.kbaje.eshop.dto.ProductDto;
import com.kbaje.eshop.dto.UserDto;
import com.kbaje.eshop.models.AppUser;
import com.kbaje.eshop.models.Authority;
import com.kbaje.eshop.models.Cart;
import com.kbaje.eshop.models.CartProduct;
import com.kbaje.eshop.models.Product;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class MapperProfile {

    public abstract ProductDto productToDto(Product product);

    public abstract Iterable<ProductDto> productsToDto(Iterable<Product> products);

    public abstract CartProductDto cartProductToDto(CartProduct cartProduct);
    public abstract Iterable<CartProductDto> cartProductsToDto(Iterable<CartProduct> cartProducts);

    public abstract CartDto cartToDto(Cart cart);
    public abstract Iterable<CartDto> cartsToDto(Iterable<Cart> carts);

    public abstract UserDto userToDto(AppUser user);
    public abstract Iterable<UserDto> usersToDto(Iterable<AppUser> users);
    
    @AfterMapping
    public void afterUserMapping(AppUser user, @MappingTarget UserDto userDto) {
        userDto.role = user.getAuthorities().contains(Authority.ADMIN) ? "ADMIN" : "USER";
    }
}
