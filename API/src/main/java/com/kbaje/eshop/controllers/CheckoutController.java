package com.kbaje.eshop.controllers;

import com.kbaje.eshop.dto.AddProductToCartDto;
import com.kbaje.eshop.dto.CartDto;
import com.kbaje.eshop.services.CheckoutService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @Operation(summary = "Create cart", description = "Create cart", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("createCart")
    public CartDto createCart() {
        return checkoutService.createCart();
    }

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
}
