package com.kbaje.eshop.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.kbaje.eshop.exceptions.IllegalCartStateException;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Cart extends BaseEntity {

    @ManyToOne
    private AppUser user;

    @Enumerated(EnumType.STRING)
    private CartState state;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "cart")
    private List<CartProduct> products;

    protected Cart() {

    }

    protected Cart(AppUser user) {
        super();

        this.user = user;
        this.state = CartState.NEW;
    }

    public List<CartProduct> getProducts() {
        return products;
    }

    public CartState getState() {
        return state;
    }

    public static Cart create(AppUser user) {
        return new Cart(user);
    }

    public void order() {
        this.state = CartState.ORDERED;
    }

    public void addProduct(CartProduct product) {
        if (this.state != CartState.NEW) {
            throw new IllegalCartStateException("Cannot add product to cart in state " + this.state);
        }

        if (this.products.stream().filter(p -> p.getProduct().getId().equals(product.getProduct().getId()))
                .count() > 0) {
            throw new IllegalCartStateException(String.format("Product '%s' already in cart", product.getProduct().getName()));
        }

        products.add(product);
    }
}
