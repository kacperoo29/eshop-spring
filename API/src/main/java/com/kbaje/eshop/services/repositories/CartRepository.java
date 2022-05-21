package com.kbaje.eshop.services.repositories;

import java.util.UUID;

import com.kbaje.eshop.models.Cart;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends BaseRepository<Cart> {

    @Query("SELECT c FROM Cart c WHERE c.user.id = ?1 AND c.state = 'ORDERED'")
    Iterable<Cart> getUserOrders(UUID id);

    @Query("SELECT c FROM Cart c WHERE c.user.id = ?1 AND c.state = 'NEW'")
    Iterable<Cart> getUserCarts(UUID id);
    
}
