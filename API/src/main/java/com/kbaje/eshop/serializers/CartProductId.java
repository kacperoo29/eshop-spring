package com.kbaje.eshop.serializers;

import java.io.Serializable;
import java.util.UUID;

public class CartProductId implements Serializable {

    private UUID cart;
    private UUID product;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CartProductId other = (CartProductId) obj;
        if (this.cart != other.cart) {
            return false;
        }
        if (this.product != other.product) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.cart != null ? this.cart.hashCode() : 0);
        hash = 97 * hash + (this.product != null ? this.product.hashCode() : 0);
        return hash;
    }

    public UUID getCart() {
        return cart;
    }

    public void setCart(UUID cart) {
        this.cart = cart;
    }

    public UUID getProduct() {
        return product;
    }

    public void setProduct(UUID product) {
        this.product = product;
    }

}
