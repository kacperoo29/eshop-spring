package com.kbaje.eshop.models;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
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
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartProduct> products;

    protected Cart() {
        this.products = new LinkedList<>();
    }

    protected Cart(AppUser user) {
        super();

        this.user = user;
        this.state = CartState.NEW;
        this.products = new LinkedList<>();
    }

    public static Cart create(AppUser user) {
        return new Cart(user);
    }

    public AppUser getUser() {
        return user;
    }

    public List<CartProduct> getProducts() {
        return products;
    }

    public CartState getState() {
        return state;
    }

    public void postOrder() {
        if (state != CartState.NEW) {
            throw new IllegalCartStateException("Cannot post order from cart with state " + state);
        }

        if (products.isEmpty()) {
            throw new IllegalCartStateException("Cannot post order from cart with no products");
        }

        this.state = CartState.ORDERED;
    }

    public void addProduct(CartProduct product) {
        if (this.state != CartState.NEW) {
            throw new IllegalCartStateException("Cannot add product to cart in state " + this.state);
        }

        if (this.products.stream().filter(p -> p.getProduct().getId().equals(product.getProduct().getId()))
                .count() > 0) {
            throw new IllegalCartStateException(
                    String.format("Product '%s' already in cart", product.getProduct().getName()));
        }

        products.add(product);
    }

    public void changeQuantity(Product product, int quantity) {
        if (this.state != CartState.NEW) {
            throw new IllegalCartStateException("Cannot change quantity of cart in state " + this.state);
        }

        CartProduct cartProduct = this.products.stream().filter(p -> p.getProduct().getId().equals(product.getId()))
                .findFirst().orElse(null);

        if (cartProduct == null) {
            throw new IllegalCartStateException(
                    String.format("Product '%s' not in cart", product.getName()));
        }

        cartProduct.setQuantity(quantity);
    }

    public void removeProduct(Product product) {
        if (this.state != CartState.NEW) {
            throw new IllegalCartStateException("Cannot remove product from cart in state " + this.state);
        }

        CartProduct cartProduct = this.products.stream().filter(p -> p.getProduct().getId().equals(product.getId()))
                .findFirst().orElse(null);

        if (cartProduct == null) {
            throw new IllegalCartStateException(
                    String.format("Product '%s' not in cart", product.getName()));
        }

        this.products.remove(cartProduct);
    }
}
