package com.kbaje.eshop.controllers;

import com.kbaje.eshop.Dto.CreateProductDto;
import com.kbaje.eshop.models.Product;
import com.kbaje.eshop.services.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductRepository repository;

    @Autowired
    public ProductController(ProductRepository repository) {
        super();

        this.repository = repository;
    }

    @GetMapping("getAll")
    public Iterable<Product> Get() {
        return repository.findAll();
    }

    @PostMapping("create")
    public Product Create(@RequestBody CreateProductDto request) {
        Product entity = Product.Create(request.name, request.description);

        return repository.save(entity);
    }
}
