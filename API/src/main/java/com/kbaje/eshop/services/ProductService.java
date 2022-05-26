package com.kbaje.eshop.services;

import java.util.UUID;

import com.kbaje.eshop.dto.CreateProductDto;
import com.kbaje.eshop.dto.EditProductDto;
import com.kbaje.eshop.dto.ProductDto;
import com.kbaje.eshop.exceptions.EntityNotFoundException;
import com.kbaje.eshop.mapping.MapperProfile;
import com.kbaje.eshop.models.Product;
import com.kbaje.eshop.services.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private ProductRepository repository;

    private MapperProfile mapper;

    @Autowired
    public ProductService(ProductRepository repostiory, MapperProfile mapper) {
        this.repository = repostiory;
        this.mapper = mapper;
    }

    public ProductDto getById(UUID productId) {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(Product.class, productId));

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
        Product entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Product.class, id));

        entity.editName(payload.name);
        entity.editDescription(payload.description);
        entity.editImageUrl(payload.imageUrl);
        entity.editPrice(payload.price);

        Product product = repository.save(entity);

        return mapper.productToDto(product);
    }

    public void removeProduct(UUID productId) {
        repository.deleteById(productId);
    }

}
