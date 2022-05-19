package com.kbaje.eshop.controllers;

import java.util.UUID;

import com.kbaje.eshop.dto.CreateProductDto;
import com.kbaje.eshop.dto.EditProductDto;
import com.kbaje.eshop.dto.ProductDto;
import com.kbaje.eshop.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("getById/{id}")
    public ProductDto getById(@PathVariable UUID id) {
        return productService.getById(id);
    }

    @GetMapping("getAll")
    public Iterable<ProductDto> get() {
        return productService.getAll();
    }

    @PostMapping("create")
    public ProductDto create(@RequestBody CreateProductDto payload) {
        return productService.createProduct(payload);
    }

    @PutMapping("edit/{id}")
    public ProductDto edit(@PathVariable UUID id, @RequestBody EditProductDto payload) {
        return productService.editProduct(id, payload);
    }

    @DeleteMapping("remove/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void remove(@PathVariable UUID id) {
        productService.removeProduct(id);
    }
}
