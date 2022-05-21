package com.kbaje.eshop.services.repositories;

import com.kbaje.eshop.models.CartProduct;
import com.kbaje.eshop.serializers.CartProductId;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartProductRepository extends CrudRepository<CartProduct, CartProductId> {

}
