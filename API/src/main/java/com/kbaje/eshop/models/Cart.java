package com.kbaje.eshop.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Cart extends BaseEntity {

    @ManyToOne
    private AppUser user;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "cart")
    private List<CartProduct> products;

    protected Cart() {

    }

    protected Cart(AppUser user) {
        super();

        this.user = user;
    }
    
    public static Cart create(AppUser user) {
        return new Cart(user);
    }

    public List<CartProduct> getProducts() {
        return products;
    }

    public void addProduct(CartProduct product) {
        products.add(product);
    }
}
