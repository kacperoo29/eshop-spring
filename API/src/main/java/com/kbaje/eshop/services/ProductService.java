package com.kbaje.eshop.services;

import java.util.UUID;

import com.kbaje.eshop.dto.CreateProductDto;
import com.kbaje.eshop.dto.EditProductDto;
import com.kbaje.eshop.dto.ProductDto;
import com.kbaje.eshop.mapping.MapperProfile;
import com.kbaje.eshop.models.Product;
import com.kbaje.eshop.services.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private MapperProfile mapper;

    public ProductDto getById(UUID productId) {
        Product product = repository.findById(productId).get();

        return mapper.productToDto(product);
    }

    public Iterable<ProductDto> getAll() {
        Iterable<Product> products = repository.findAll();

        return mapper.productsToDto(products);
    }

    public ProductDto createProduct(CreateProductDto payload) {
        Product entity = Product.create(payload.name, payload.description, payload.price, payload.imageUrl);
        Product product = repository.save(entity);

        return mapper.productToDto(product);
    }

    public ProductDto editProduct(UUID id, EditProductDto payload) {
        Product entity = repository.findById(id).get();

        entity.editName(payload.name);
        entity.editDescription(payload.description);

        Product product = repository.save(entity);

        return mapper.productToDto(product);
    }

    public void removeProduct(UUID productId) {
        repository.deleteById(productId);
    }

}
