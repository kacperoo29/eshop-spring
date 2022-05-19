package com.kbaje.eshop.mapping;

import com.kbaje.eshop.dto.ProductDto;
import com.kbaje.eshop.models.Product;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    
    ProductMapper Instance = Mappers.getMapper(ProductMapper.class);

    ProductDto mapToDto(Product product);

    Iterable<ProductDto> mapMultipleToDto(Iterable<Product> products);
}
