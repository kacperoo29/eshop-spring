package com.kbaje.eshop.services;

import java.util.UUID;

import com.kbaje.eshop.dto.AddProductToCartDto;
import com.kbaje.eshop.dto.CartDto;
import com.kbaje.eshop.dto.ChangeQuantityDto;
import com.kbaje.eshop.exceptions.EntityNotFoundException;
import com.kbaje.eshop.exceptions.IllegalCartStateException;
import com.kbaje.eshop.mapping.MapperProfile;
import com.kbaje.eshop.models.AppUser;
import com.kbaje.eshop.models.Cart;
import com.kbaje.eshop.models.CartProduct;
import com.kbaje.eshop.models.Product;
import com.kbaje.eshop.services.repositories.CartRepository;
import com.kbaje.eshop.services.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class CheckoutService {

    private CartRepository cartRepository;

    private MapperProfile mapper;

    private UserService userService;

    private ProductRepository productRepository;

    private JavaMailSender mailSender;

    @Autowired
    public CheckoutService(CartRepository cartRepository, MapperProfile mapper, UserService userService,
            ProductRepository productRepository, JavaMailSender sender) {
        this.cartRepository = cartRepository;
        this.mapper = mapper;
        this.userService = userService;
        this.productRepository = productRepository;
        this.mailSender = sender;
    }

    public CartDto addProductToCart(AddProductToCartDto dto) {
        Cart cart = getUserCartImpl();
        Product product = productRepository.findById(dto.productId)
                .orElseThrow(() -> new EntityNotFoundException(Product.class, dto.productId));
        CartProduct cartProduct = new CartProduct(cart, product, dto.quantity);
        cart.addProduct(cartProduct);

        return mapper.cartToDto(cartRepository.save(cart));
    }

    public Iterable<CartDto> getUserOrders() {
        AppUser userDetails = userService.getCurrentUser();

        return mapper.cartsToDto(cartRepository.getUserOrders(userDetails.getId()));
    }

    public CartDto getUserCart() {
        return mapper.cartToDto(getUserCartImpl());
    }

    public CartDto postOrder() {
        Cart cart = getUserCartImpl();
        cart.postOrder();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(cart.getUser().getEmail());
        message.setSubject("Order confirmation");
        StringBuilder sb = new StringBuilder();
        sb.append("Thank you for your order!\n");
        sb.append("Your order number is: ").append(cart.getId()).append("\n");
        sb.append("Your order contents:\n");
        for (CartProduct product : cart.getProducts()) {
            sb.append(product.getProduct().getName()).append(" x ").append(product.getQuantity()).append("\n");
        }

        message.setText(sb.toString());
        mailSender.send(message);

        return mapper.cartToDto(cartRepository.save(cart));
    }

    public CartDto changeQuantity(ChangeQuantityDto payload) {
        Cart cart = getUserCartImpl();
        Product product = productRepository.findById(payload.productId)
                .orElseThrow(() -> new EntityNotFoundException(Product.class, payload.productId));

        cart.changeQuantity(product, payload.quantity);

        cartRepository.save(cart);

        return mapper.cartToDto(cart);
    }

    public CartDto removeProductFromCart(UUID productId) {
        Cart cart = getUserCartImpl();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(Product.class, productId));

        cart.removeProduct(product);

        cartRepository.save(cart);

        return mapper.cartToDto(cart);
    }

    private Cart getUserCartImpl() {
        Iterable<Cart> carts = cartRepository.getUserCarts(userService.getCurrentUser().getId());
        if (carts.iterator().hasNext()) {
            return carts.iterator().next();
        } else {
            return createCartImpl();
        }
    }

    private Cart createCartImpl() {
        AppUser userDetails = userService.getCurrentUser();
        Iterable<Cart> carts = cartRepository.getUserCarts(userDetails.getId());
        if (carts.iterator().hasNext()) {
            throw new IllegalCartStateException("User already has a cart");
        }

        Cart cart = Cart.create(userDetails);
        cartRepository.save(cart);

        return cart;
    }
}
