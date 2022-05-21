package com.kbaje.eshop.controllers;

import java.util.UUID;

import com.kbaje.eshop.dto.CreateProductDto;
import com.kbaje.eshop.dto.EditProductDto;
import com.kbaje.eshop.dto.ProductDto;
import com.kbaje.eshop.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Products", description = "Operations about products")
@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "Get product by id", description = "Get product by id")
    @GetMapping("getById/{id}")
    public ProductDto getById(@PathVariable UUID id) {
        return productService.getById(id);
    }

    @Operation(summary = "Get all products", description = "Get all products")
    @GetMapping("getAll")
    public Iterable<ProductDto> get() {
        return productService.getAll();
    }

    @Operation(summary = "Create product", description = "Create product", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("create")
    public ProductDto create(@RequestBody CreateProductDto payload) {
        return productService.createProduct(payload);
    }

    @Operation(summary = "Edit product", description = "Edit product", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("edit/{id}")
    public ProductDto edit(@PathVariable UUID id, @RequestBody EditProductDto payload) {
        return productService.editProduct(id, payload);
    }

    @Operation(summary = "Delete product", description = "Delete product", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("remove/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void remove(@PathVariable UUID id) {
        productService.removeProduct(id);
    }
}
