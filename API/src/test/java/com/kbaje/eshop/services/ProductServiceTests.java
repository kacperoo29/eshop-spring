package com.kbaje.eshop.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.kbaje.eshop.dto.CreateProductDto;
import com.kbaje.eshop.dto.ProductDto;
import com.kbaje.eshop.mapping.MapperProfile;
import com.kbaje.eshop.models.Product;
import com.kbaje.eshop.services.repositories.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductServiceTests {

    @Mock
    private ProductRepository productRepository;

    @Autowired
    private MapperProfile mapperProfile = Mappers.getMapper(MapperProfile.class);

    private ProductService productService;

    private List<Product> testProducts;

    @BeforeEach
    public void setUp() {
        testProducts = new ArrayList<>(List.of(
                Product.create("testP1", "testD1", new BigDecimal(1), "img.png"),
                Product.create("testP2", "testD2", new BigDecimal(1), "img.png"),
                Product.create("testP3", "testD3", new BigDecimal(1), "img.png"),
                Product.create("testP4", "testD4", new BigDecimal(1), "img.png")));
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository, mapperProfile);

        when(productRepository.findById(any(UUID.class))).thenAnswer(value -> testProducts.stream()
                .filter(p -> p.getId().equals(value.getArgument(0, UUID.class))).findFirst());
        when(productRepository.findAll()).thenReturn(testProducts);
        when(productRepository.save(any(Product.class))).thenAnswer(answer -> {
            testProducts.add(answer.getArgument(0));

            return answer.getArgument(0);
        });
    }

    @Test
    public void shouldGetById() {
        UUID id = testProducts.get(0).getId();
        ProductDto productDto = productService.getById(id);

        assert productDto.id.equals(id);
        assert productDto.name.equals(testProducts.get(0).getName());
        assert productDto.description.equals(testProducts.get(0).getDescription());
        assert productDto.price.equals(testProducts.get(0).getPrice());
        assert productDto.imageUrl.equals(testProducts.get(0).getImageUrl());
    }

    @Test
    public void shouldGetAllProducts() {
        Iterable<ProductDto> iterableProducts = productService.getAll();
        List<ProductDto> products = new ArrayList<ProductDto>();
        iterableProducts.forEach(products::add);

        assert products.size() == testProducts.size();
        // Not always the case, refactor
        for (int i = 0; i < products.size(); i++) {
            assert products.get(i).id.equals(testProducts.get(i).getId());
            assert products.get(i).name.equals(testProducts.get(i).getName());
            assert products.get(i).description.equals(testProducts.get(i).getDescription());
            assert products.get(i).price.equals(testProducts.get(i).getPrice());
            assert products.get(i).imageUrl.equals(testProducts.get(i).getImageUrl());
        }
    }

    @Test
    public void shouldCreateProduct() {
        CreateProductDto payload = new CreateProductDto();
        payload.name = "NewName";
        payload.description = "NewDesc";
        payload.imageUrl = "newimg.png";
        payload.price = new BigDecimal(2);

        ProductDto newProduct = productService.createProduct(payload);

        assert testProducts.stream().anyMatch(x -> x.getId() == newProduct.id);
    }

}
