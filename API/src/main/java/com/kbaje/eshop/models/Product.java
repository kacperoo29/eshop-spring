package com.kbaje.eshop.models;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import com.kbaje.eshop.exceptions.EmptyFieldException;
import com.kbaje.eshop.exceptions.InvalidPriceException;

@Entity
public final class Product extends BaseEntity {
    
    @Column(length = 100)
    @Size(min = 1, max = 100)
    private String name;

    @Column(length = 1000)
    @Size(min = 5, max = 1000)
    private String description;

    @Column(length = 1000)
    @Size(min = 5, max = 1000)
    private String imageUrl;

    @Column(scale = 2)
    private BigDecimal price;

    @OneToMany(mappedBy = "product")
    private List<CartProduct> carts;

    protected Product() {
        
    }

    protected Product(String name, String description, BigDecimal price, String imageUrl) {
        super();

        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static Product create(String name, String description, BigDecimal price, String imageUrl) {
        if (name.isEmpty()) {
            throw new EmptyFieldException(Product.class, "name");
        }

        if (description.isEmpty()) {
            throw new EmptyFieldException(Product.class, "description");
        }

        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidPriceException(price);
        }

        return new Product(name, description, price, imageUrl);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void editName(String name) {
        if (name.isEmpty()) {
            throw new EmptyFieldException(Product.class, "name");
        }

        this.name = name;
    }

    public void editDescription(String description) {
        if (description.isEmpty()) {
            throw new EmptyFieldException(Product.class, "description");
        }

        this.description = description;
    }

    public void editPrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidPriceException(price);
        }

        this.price = price;
    }

    public void editImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
