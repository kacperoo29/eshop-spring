package com.kbaje.eshop.models;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import com.kbaje.eshop.exceptions.EmptyFieldException;
import com.kbaje.eshop.exceptions.InvalidPriceException;

import org.junit.jupiter.api.Test;

public class ProductTests {

    @Test
    public void shouldThrowWhenEmptyName() {
        EmptyFieldException e = assertThrows(EmptyFieldException.class,
                () -> Product.create("", "desc", new BigDecimal(1), "img.png"));

        assert e.getMessage().equals("Field name in class Product can't be empty");
    }

    @Test
    public void shouldThrowWhenEmptyDescription() {
        EmptyFieldException e = assertThrows(EmptyFieldException.class,
                () -> Product.create("name", "", new BigDecimal(1), "img.png"));

        assert e.getMessage().equals("Field description in class Product can't be empty");
    }

    @Test
    public void shouldThrowWhenInvalidPrice() {
        InvalidPriceException e = assertThrows(InvalidPriceException.class,
                () -> Product.create("name", "desc", new BigDecimal(-1), "img.png"));

        assert e.getMessage().equals("Invalid price: -1");
    }

    @Test
    public void shouldThrowWhenEditingToEmptyName() {
        EmptyFieldException e = assertThrows(EmptyFieldException.class,
                () -> Product.create("name", "desc", new BigDecimal(1), "img.png").editName(""));

        assert e.getMessage().equals("Field name in class Product can't be empty");
    }

    @Test
    public void shouldThrowWhenEditingToEmptyDescription() {
        EmptyFieldException e = assertThrows(EmptyFieldException.class,
                () -> Product.create("name", "desc", new BigDecimal(1), "img.png").editDescription(""));

        assert e.getMessage().equals("Field description in class Product can't be empty");
    }

    @Test
    public void shouldThrowWhenEditingToInvalidPrice() {
        InvalidPriceException e = assertThrows(InvalidPriceException.class,
                () -> Product.create("name", "desc", new BigDecimal(1), "img.png").editPrice(new BigDecimal(-1)));

        assert e.getMessage().equals("Invalid price: -1");
    }

    @Test
    public void shouldCreateCorrectProduct() {
        Product product = Product.create("name", "desc", new BigDecimal(1), "img.png");

        assert product.getName().equals("name");
        assert product.getDescription().equals("desc");
        assert product.getPrice().equals(new BigDecimal(1));
        assert product.getImageUrl().equals("img.png");
    }

    @Test
    public void shouldEditName() {
        Product product = Product.create("name", "desc", new BigDecimal(1), "img.png");

        product.editName("newName");

        assert product.getName().equals("newName");
    }

    @Test
    public void shouldEditDescription() {
        Product product = Product.create("name", "desc", new BigDecimal(1), "img.png");

        product.editDescription("newDesc");

        assert product.getDescription().equals("newDesc");
    }

    @Test
    public void shouldEditPrice() {
        Product product = Product.create("name", "desc", new BigDecimal(1), "img.png");

        product.editPrice(new BigDecimal(2));

        assert product.getPrice().equals(new BigDecimal(2));
    }

    @Test
    public void shouldEditImageUrl() {
        Product product = Product.create("name", "desc", new BigDecimal(1), "img.png");

        product.editImageUrl("newImg.png");

        assert product.getImageUrl().equals("newImg.png");
    }
}
 