package com.kbaje.eshop.services;

import java.util.UUID;

import com.kbaje.eshop.dto.CreateProductDto;
import com.kbaje.eshop.dto.EditProductDto;
import com.kbaje.eshop.dto.ProductDto;
import com.kbaje.eshop.mapping.ProductMapper;
import com.kbaje.eshop.models.Product;
import com.kbaje.eshop.services.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductMapper mapper;

    public ProductDto getById(UUID productId) {
        Product product = repository.findById(productId).get();

        return mapper.mapToDto(product);
    }

    public Iterable<ProductDto> getAll() {
        Iterable<Product> products = repository.findAll();

        return mapper.mapMultipleToDto(products);
    }

    public ProductDto createProduct(CreateProductDto payload) {
        Product entity = Product.Create(payload.name, payload.description);
        Product product = repository.save(entity);

        return mapper.mapToDto(product);
    }

    public ProductDto editProduct(UUID id, EditProductDto payload) {
        Product entity = repository.findById(id).get();

        entity.editName(payload.name);
        entity.editDescription(payload.description);

        Product product = repository.save(entity);

        return mapper.mapToDto(product);
    }

    public void removeProduct(UUID productId) {
        repository.deleteById(productId);
    }

}
