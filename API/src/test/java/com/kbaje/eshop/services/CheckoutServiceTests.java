package com.kbaje.eshop.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.kbaje.eshop.dto.AddProductToCartDto;
import com.kbaje.eshop.dto.CartDto;
import com.kbaje.eshop.dto.ChangeQuantityDto;
import com.kbaje.eshop.exceptions.EntityNotFoundException;
import com.kbaje.eshop.mapping.MapperProfile;
import com.kbaje.eshop.models.AppUser;
import com.kbaje.eshop.models.Cart;
import com.kbaje.eshop.models.CartState;
import com.kbaje.eshop.models.Product;
import com.kbaje.eshop.services.repositories.CartRepository;
import com.kbaje.eshop.services.repositories.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;

public class CheckoutServiceTests {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserService userService;

    private CheckoutService checkoutService;

    private MapperProfile mapperProfile = Mappers.getMapper(MapperProfile.class);

    private List<Product> testProducts;

    private AppUser testUser;
    private List<Cart> userCarts;

    @BeforeEach
    public void setUp() {
        testProducts = List.of(
                Product.create("Test product 1", "Test product 1 description", new BigDecimal(10.0), "image.png"),
                Product.create("Test product 2", "Test product 2 description", new BigDecimal(20.0), "image.png"),
                Product.create("Test product 3", "Test product 3 description", new BigDecimal(30.0), "image.png"));

        productRepository = mock(ProductRepository.class);

        when(productRepository.findById(any(UUID.class))).thenAnswer(value -> testProducts.stream()
                .filter(p -> p.getId().equals(value.getArgument(0, UUID.class))).findFirst());

        doAnswer(answer -> {
            if (testProducts.stream()
                    .filter(p -> p.getId().equals(answer.getArgument(0, Product.class).getId()))
                    .findFirst()
                    .isPresent()) {
                return answer.getArgument(0, Product.class);
            } else {
                testProducts.add(answer.getArgument(0, Product.class));
                return answer.getArgument(0, Product.class);
            }
        }).when(productRepository).save(any(Product.class));

        when(productRepository.findAll()).thenReturn(testProducts);

        testUser = AppUser.create("username", "test@test.com", "P@ssword1");

        userService = mock(UserService.class);
        when(userService.getCurrentUser()).thenReturn(testUser);

        userCarts = new ArrayList<Cart>();
        cartRepository = mock(CartRepository.class);
        when(cartRepository.getUserCarts(any(UUID.class))).thenAnswer(answer -> {
            return userCarts;
        });

        doAnswer(answer -> {
            userCarts.add(answer.getArgument(0, Cart.class));

            return answer.getArgument(0, Cart.class);
        }).when(cartRepository).save(any(Cart.class));

        when(cartRepository.getUserOrders(any(UUID.class))).thenAnswer(answer -> {
            return userCarts.stream()
                    .filter(c -> c.getState() == CartState.ORDERED)
                    .collect(Collectors.toList());
        });

        checkoutService = new CheckoutService(cartRepository, mapperProfile, userService, productRepository);
    }

    @Test
    public void shouldGetNewCart() {
        CartDto cart = checkoutService.getUserCart();

        assert cart != null;
        assert cart.products.isEmpty();
    }

    @Test
    public void shouldReturnAlreadyExistingCart() {
        CartDto cart = checkoutService.getUserCart();
        CartDto cart2 = checkoutService.getUserCart();

        assert cart != null;
        assert cart.products.isEmpty();
        assert cart.id.equals(cart2.id);
    }

    @Test
    public void shouldAddProductToCart() {
        AddProductToCartDto product = new AddProductToCartDto(testProducts.get(0).getId(), 2);

        checkoutService.addProductToCart(product);
        CartDto cart = checkoutService.getUserCart();

        assert cart.products.size() == 1;
        assert cart.products.get(0).product.id.equals(testProducts.get(0).getId());
        assert cart.products.get(0).quantity == 2;
    }

    @Test
    public void shouldThrowWhenAddingNotExistingProduct() {
        AddProductToCartDto product = new AddProductToCartDto(UUID.randomUUID(), 2);

        assertThrows(EntityNotFoundException.class, () -> checkoutService.addProductToCart(product));
    }

    @Test
    public void shouldPostOrder() {
        checkoutService.getUserCart();
        checkoutService.addProductToCart(new AddProductToCartDto(testProducts.get(0).getId(), 2));
        checkoutService.addProductToCart(new AddProductToCartDto(testProducts.get(1).getId(), 3));
        checkoutService.addProductToCart(new AddProductToCartDto(testProducts.get(2).getId(), 4));

        checkoutService.postOrder();

        CartDto cart = checkoutService.getUserCart();

        assert cart.state.equals(CartState.ORDERED);
        assert cart.products.size() == 3;
    }

    @Test
    public void shouldGetUserOrders() {
        checkoutService.addProductToCart(new AddProductToCartDto(testProducts.get(0).getId(), 2));
        checkoutService.postOrder();

        Iterable<CartDto> orders = checkoutService.getUserOrders();

        assert orders.iterator().hasNext();
        assert orders.iterator().next().state.equals(CartState.ORDERED);
    }

    @Test
    public void shouldChangeQuanitity() {
        checkoutService.getUserCart();
        checkoutService.addProductToCart(new AddProductToCartDto(testProducts.get(0).getId(), 2));
        checkoutService.addProductToCart(new AddProductToCartDto(testProducts.get(1).getId(), 3));
        checkoutService.addProductToCart(new AddProductToCartDto(testProducts.get(2).getId(), 4));

        ChangeQuantityDto request = new ChangeQuantityDto(testProducts.get(0).getId(), 3);
        checkoutService.changeQuantity(request);

        CartDto cart = checkoutService.getUserCart();

        assert cart.products.size() == 3;
        assert cart.products.get(0).quantity == 3;
    }

    @Test
    public void shouldRemoveProductFromCart() {
        checkoutService.getUserCart();
        checkoutService.addProductToCart(new AddProductToCartDto(testProducts.get(0).getId(), 2));
        checkoutService.addProductToCart(new AddProductToCartDto(testProducts.get(1).getId(), 3));
        checkoutService.addProductToCart(new AddProductToCartDto(testProducts.get(2).getId(), 4));

        checkoutService.removeProductFromCart(testProducts.get(0).getId());

        CartDto cart = checkoutService.getUserCart();

        assert cart.products.size() == 2;
        assert cart.products.get(0).product.id.equals(testProducts.get(1).getId());
    }
}
