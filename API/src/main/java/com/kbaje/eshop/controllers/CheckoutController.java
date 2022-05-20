package com.kbaje.eshop.controllers;

import com.kbaje.eshop.dto.CartDto;
import com.kbaje.eshop.services.CheckoutService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/checkout")
public class CheckoutController {
    
    @Autowired
    private CheckoutService checkoutService;

    @PostMapping("createCart")
    public CartDto createCart() {
        return checkoutService.createCart();
    }

}
