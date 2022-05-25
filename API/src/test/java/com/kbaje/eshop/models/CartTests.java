package com.kbaje.eshop.models;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import com.kbaje.eshop.exceptions.IllegalCartStateException;

import org.junit.jupiter.api.Test;

public class CartTests {

    private AppUser user = AppUser.create("username", "test@test.com", "P@ssword1");
    private Product product = Product.create("name", "desc", new BigDecimal(1), "img.png");

    @Test
    public void shouldCreateCart() {
        Cart cart = Cart.create(user);

        assert cart.getState() == CartState.NEW;
        assert cart.getProducts().isEmpty();
    }

    @Test
    public void shouldAddProduct() {
        Cart cart = Cart.create(user);
        cart.addProduct(new CartProduct(cart, product, 1));

        assert cart.getProducts().size() == 1;
        assert cart.getProducts().get(0).getProduct() == product;
        assert cart.getProducts().get(0).getQuantity() == 1;
    }

    @Test
    public void shouldntAddDuplicateProducts() {
        Cart cart = Cart.create(user);
        cart.addProduct(new CartProduct(cart, product, 1));

        assertThrows(IllegalCartStateException.class, () -> cart.addProduct(new CartProduct(cart, product, 1)));

        assert cart.getProducts().size() == 1;
        assert cart.getProducts().get(0).getProduct() == product;
        assert cart.getProducts().get(0).getQuantity() == 1;
    }

    @Test
    public void shouldntAddProductInInvalidState() {
        Cart cart = Cart.create(user);
        cart.addProduct(new CartProduct(cart, product, 1));
        cart.postOrder();

        assertThrows(IllegalCartStateException.class, () -> cart.addProduct(new CartProduct(cart, product, 1)));

        assert cart.getProducts().size() == 1;
   }

   @Test
   public void shouldChangeQuantity() {
       Cart cart = Cart.create(user);
       cart.addProduct(new CartProduct(cart, product, 1));
       cart.changeQuantity(cart.getProducts().get(0).getProduct(), 2);

       assert cart.getProducts().size() == 1;
       assert cart.getProducts().get(0).getProduct() == product;
       assert cart.getProducts().get(0).getQuantity() == 2;
   }
   
   @Test
   public void shouldntChangeQuantityInInvalidState() {
       Cart cart = Cart.create(user);
       cart.addProduct(new CartProduct(cart, product, 1));
       cart.postOrder();

       assertThrows(IllegalCartStateException.class,
               () -> cart.changeQuantity(cart.getProducts().get(0).getProduct(), 2));

       assert cart.getProducts().size() == 1;
   }
   
   @Test
   public void shouldntChangeQuantityToNegative() {
       Cart cart = Cart.create(user);
       cart.addProduct(new CartProduct(cart, product, 1));

       assertThrows(IllegalArgumentException.class,
               () -> cart.changeQuantity(cart.getProducts().get(0).getProduct(), -1));

       assert cart.getProducts().size() == 1;
       assert cart.getProducts().get(0).getProduct() == product;
       assert cart.getProducts().get(0).getQuantity() == 1;
   }
   
   @Test
   public void shouldRemoveProduct() {
       Cart cart = Cart.create(user);
       cart.addProduct(new CartProduct(cart, product, 1));
       cart.removeProduct(cart.getProducts().get(0).getProduct());

       assert cart.getProducts().isEmpty();
   }
   
   @Test
   public void shouldntRemoveProductInInvalidState() {
       Cart cart = Cart.create(user);
       cart.addProduct(new CartProduct(cart, product, 1));
       cart.postOrder();

       assertThrows(IllegalCartStateException.class,
               () -> cart.removeProduct(cart.getProducts().get(0).getProduct()));

       assert cart.getProducts().size() == 1;
   }

   @Test
   public void shouldntRemoveProductNotInCart() {
       Cart cart = Cart.create(user);
       cart.addProduct(new CartProduct(cart, product, 1));

       assertThrows(IllegalCartStateException.class,
               () -> cart.removeProduct(Product.create("name", "desc", new BigDecimal(1), "img.png")));

       assert cart.getProducts().size() == 1;
   }

   @Test
   public void shouldntChangeQuantityWhenNotInCart() {
         Cart cart = Cart.create(user);
         cart.addProduct(new CartProduct(cart, product, 1));
    
         assertThrows(IllegalCartStateException.class,
                () -> cart.changeQuantity(Product.create("name", "desc", new BigDecimal(1), "img.png"), 2));
    
         assert cart.getProducts().size() == 1;
         assert cart.getProducts().get(0).getProduct() == product;
         assert cart.getProducts().get(0).getQuantity() == 1;
   }
    
    @Test
    public void shouldPostOrder() {
        Cart cart = Cart.create(user);
        cart.addProduct(new CartProduct(cart, product, 1));
        cart.postOrder();

        assert cart.getState() == CartState.ORDERED;
    }

    @Test
    public void shouldntPostOrderInInvalidState() {
        Cart cart = Cart.create(user);
        cart.addProduct(new CartProduct(cart, product, 1));
        cart.postOrder();

        assertThrows(IllegalCartStateException.class, () -> cart.postOrder());

        assert cart.getState() == CartState.ORDERED;
    }

    @Test
    public void shouldntPostEmptyOrder() {
        Cart cart = Cart.create(user);

        assertThrows(IllegalCartStateException.class, () -> cart.postOrder());

        assert cart.getState() == CartState.NEW;
    }

    @Test
    public void shouldntCreateCartProductWithInvalidQuantity() {
        assertThrows(IllegalArgumentException.class, () -> new CartProduct(Cart.create(user), product, -1));
    }
}
