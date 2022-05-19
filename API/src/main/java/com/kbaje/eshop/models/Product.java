package com.kbaje.eshop.models;

import javax.persistence.Entity;

@Entity
public final class Product extends BaseModel {
    private String name;
    private String description;

    protected Product() {
        
    }

    protected Product(String name, String description) {
        super();

        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static Product Create(String name, String description) {
        return new Product(name, description);
    }
}
