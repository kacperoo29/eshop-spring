package com.kbaje.eshop.services;

import com.kbaje.eshop.dto.CartDto;
import com.kbaje.eshop.mapping.CartMapper;
import com.kbaje.eshop.services.repositories.CartRepository;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckoutService {
    
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartMapper cartMapper;

    public CartDto createCart() {
        throw new NotImplementedException();
    }

}
