package com.kbaje.eshop.models;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

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

    public static Product create(String name, String description, BigDecimal price, String imageUrl) {
        return new Product(name, description, price, imageUrl);
    }

    public void editName(String name) {
        this.name = name;
    }

    public void editDescription(String description) {
        this.description = description;
    }

    public void editPrice(BigDecimal price) {
        this.price = price;
    }

    public void editImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
