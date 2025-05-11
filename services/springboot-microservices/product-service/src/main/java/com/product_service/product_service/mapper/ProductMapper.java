package com.product_service.product_service.mapper;

import com.product_service.product_service.Dto.ProductDto;
import com.product_service.product_service.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring") // Use Spring's component model
public interface ProductMapper {

    // Create a singleton instance for this mapper.
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    // Map Product entity to ProductDto
    ProductDto toDto(Product product);

    // Map ProductDto to Product entity
    Product toEntity(ProductDto productDto);
}
