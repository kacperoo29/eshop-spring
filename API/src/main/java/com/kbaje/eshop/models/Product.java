package com.kbaje.eshop.models;

import javax.persistence.Entity;

@Entity
public final class Product extends BaseEntity {
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

    public static Product create(String name, String description) {
        return new Product(name, description);
    }

    public void editName(String name) {
        this.name = name;
    }

    public void editDescription(String description) {
        this.description = description;
    }

}
