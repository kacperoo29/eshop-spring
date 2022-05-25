package com.kbaje.eshop.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.kbaje.eshop.serializers.CartProductId;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@IdClass(CartProductId.class)
public class CartProduct {
    
    @Id
    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Product product;

    private int quantity;

    protected CartProduct() {
        
    }

    public CartProduct(Cart cart, Product product, int quantity) {
        this();
        
        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public Cart getCart() {
        return cart;
    }

    public void setQuantity(int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        this.quantity = quantity;
    }
}
