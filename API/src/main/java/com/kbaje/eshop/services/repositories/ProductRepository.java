package com.kbaje.eshop.services.repositories;

import java.util.UUID;

import com.kbaje.eshop.models.Product;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, UUID> {
}
