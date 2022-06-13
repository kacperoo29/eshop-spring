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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class CheckoutService {

    private CartRepository cartRepository;

    private MapperProfile mapper;

    private UserService userService;

    private ProductRepository productRepository;

    private JavaMailSender mailSender;

    private Logger logger = LoggerFactory.getLogger(CheckoutService.class);

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
                .orElseThrow(() -> {
                    logger.error("Product with id {} not found", dto.productId);
                    return new EntityNotFoundException(Product.class, dto.productId);
                });

        CartProduct cartProduct = new CartProduct(cart, product, dto.quantity);
        cart.addProduct(cartProduct);
        logger.info("Added product with id: {} to cart with id: {}", product.getId(), cart.getId());

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
        logger.info("Order with id: {} posted", cart.getId());

        this.sendMail(cart);

        return mapper.cartToDto(cartRepository.save(cart));
    }

    @Async
    private void sendMail(Cart cart) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(cart.getUser().getEmail());
        message.setSubject("Order confirmation");
        StringBuilder sb = new StringBuilder();
        sb.append("Thank you for your order!\n");
        sb.append("Your order number is: ").append(cart.getId()).append("\n");
        sb.append("Your order contents:\n");
        for (CartProduct product : cart.getProducts()) {
            sb.append(product.getProduct().getName()).append(" x ").append(product.getQuantity()).append(" @ $")
                    .append(product.getProduct().getPrice()).append("\n");
        }

        message.setText(sb.toString());
        mailSender.send(message);
        logger.info("Mail sent to " + cart.getUser().getEmail());
    }

    public CartDto changeQuantity(ChangeQuantityDto payload) {
        Cart cart = getUserCartImpl();
        Product product = productRepository.findById(payload.productId)
                .orElseThrow(() -> {
                    logger.error("Product with id {} not found", payload.productId);
                    return new EntityNotFoundException(Product.class, payload.productId);
                });

        cart.changeQuantity(product, payload.quantity);

        cartRepository.save(cart);

        logger.info("Cart {} updated", cart.getId());
        return mapper.cartToDto(cart);
    }

    public CartDto removeProductFromCart(UUID productId) {
        Cart cart = getUserCartImpl();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(Product.class, productId));

        cart.removeProduct(product);

        cartRepository.save(cart);

        logger.info("Removed product with id: {} from cart with id: {}", product.getId(), cart.getId());
        return mapper.cartToDto(cart);
    }

    private Cart getUserCartImpl() {
        Iterable<Cart> carts = cartRepository.getUserCarts(userService.getCurrentUser().getId());
        if (carts.iterator().hasNext()) {
            logger.info("Found cart for user");
            return carts.iterator().next();
        } else {
            logger.info("Creating new cart for user");
            return createCartImpl();
        }
    }

    private Cart createCartImpl() {
        AppUser userDetails = userService.getCurrentUser();
        Iterable<Cart> carts = cartRepository.getUserCarts(userDetails.getId());
        if (carts.iterator().hasNext()) {
            logger.info("Cart already exists for user {}", userDetails.getId());
            throw new IllegalCartStateException("User already has a cart");
        }

        Cart cart = Cart.create(userDetails);
        cartRepository.save(cart);

        logger.info("Cart created for user {}", userDetails.getId());
        return cart;
    }
}
