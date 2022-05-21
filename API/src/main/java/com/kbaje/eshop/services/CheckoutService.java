package com.kbaje.eshop.services;

import java.util.UUID;

import com.kbaje.eshop.dto.AddProductToCartDto;
import com.kbaje.eshop.dto.CartDto;
import com.kbaje.eshop.exceptions.IllegalCartStateException;
import com.kbaje.eshop.mapping.MapperProfile;
import com.kbaje.eshop.models.AppUser;
import com.kbaje.eshop.models.Cart;
import com.kbaje.eshop.models.CartProduct;
import com.kbaje.eshop.models.Product;
import com.kbaje.eshop.services.repositories.CartProductRepository;
import com.kbaje.eshop.services.repositories.CartRepository;
import com.kbaje.eshop.services.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckoutService {
    
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private MapperProfile mapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartProductRepository cartProductRepository;

    public CartDto createCart() {
        AppUser userDetails = userService.getCurrentUser();
        Iterable<Cart> carts = cartRepository.getUserCarts(userDetails.getId());
        if (carts.iterator().hasNext()) {
            throw new IllegalCartStateException("User already has a cart");
        }
        
        Cart cart = Cart.create(userDetails);

        return mapper.cartToDto(cartRepository.save(cart));
    }

    public CartDto addProductToCart(UUID cartId, AddProductToCartDto dto) {
        Cart cart = cartRepository.findById(cartId).get();
        Product product = productRepository.findById(dto.productId).get();
        CartProduct cartProduct = new CartProduct(cart, product, dto.quantity);
        cart.addProduct(cartProduct);

        cartProductRepository.save(cartProduct);

        return mapper.cartToDto(cartRepository.save(cart));
    }

    public Iterable<CartDto> getUserOrders() {
        AppUser userDetails = userService.getCurrentUser();

        return mapper.cartsToDto(cartRepository.getUserOrders(userDetails.getId()));
    }    

}
