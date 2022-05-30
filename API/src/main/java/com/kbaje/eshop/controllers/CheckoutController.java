package com.kbaje.eshop.controllers;

import java.util.UUID;

import com.kbaje.eshop.dto.AddProductToCartDto;
import com.kbaje.eshop.dto.ChangeQuantityDto;
import com.kbaje.eshop.dto.CartDto;
import com.kbaje.eshop.services.CheckoutService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE })
@Tag(name = "Checkout", description = "Operations about checkout")
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @Operation(summary = "Add product to cart", description = "Add product to cart", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("addProductToCart")
    public CartDto addProductToCart(@RequestBody AddProductToCartDto payload) {
        return checkoutService.addProductToCart(payload);
    }

    @Operation(summary = "Get user orders", description = "Get user orders", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("getUserOrders")
    public Iterable<CartDto> getUserOrders() {
        return checkoutService.getUserOrders();
    }

    @Operation(summary = "Get cart", description = "Get cart", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("getCart")
    public CartDto getCart() {
        return checkoutService.getUserCart();
    }

    @Operation(summary = "Post order", description = "Post order", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("postOrder")
    public CartDto postOrder() {
        return checkoutService.postOrder();
    }

    @Operation(summary = "Change quantity", description = "Change quantity", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("changeQuantity")
    public CartDto changeQuantity(@RequestBody ChangeQuantityDto payload) {
        return checkoutService.changeQuantity(payload);
    }

    @Operation(summary = "Remove product from cart", description = "Remove product form cart", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("removeProductFromCart/{productId}")
    public CartDto removeProductFromCart(@PathVariable UUID productId) {
        return checkoutService.removeProductFromCart(productId);
    }
}
