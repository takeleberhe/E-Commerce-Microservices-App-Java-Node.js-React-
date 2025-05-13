package com.product_service.product_service.service;

import com.product_service.product_service.Dto.ProductDto;
import com.product_service.product_service.entity.Product;
import com.product_service.product_service.exception.ProductNotFoundException;
import com.product_service.product_service.kafka.ProductEvent;
import com.product_service.product_service.kafka.ProductProducer;
import com.product_service.product_service.mapper.ProductMapper;
import com.product_service.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

     // constructor based injection using lombk
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductProducer productProducer; // Inject Kafka producer

    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        log.info("Creating product: {}", productDto.getName());
        Product product = productMapper.toEntity(productDto);
        Product savedProduct = productRepository.save(product);
        ProductDto savedDto = productMapper.toDto(savedProduct);

        // Send Kafka event
        ProductEvent event = ProductEvent.builder()
                .productId(savedDto.getId())
                .name(savedDto.getName())
                .category(savedDto.getCategory())
                .price(savedDto.getPrice())
                .status("CREATED")
                .build();
        productProducer.sendProductEvent(event);

        return savedDto;
    }

    public ProductDto getProductById(Long id) {
        log.info("Fetching product with ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
        return productMapper.toDto(product);
    }

    public List<ProductDto> getAllProducts() {
        log.info("Fetching all products");
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        log.info("Updating product with ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());
        product.setCategory(productDto.getCategory());
        product.setImageUrl(productDto.getImageUrl());

        Product updatedProduct = productRepository.save(product);
        ProductDto updatedDto = productMapper.toDto(updatedProduct);

             // Send Kafka event
        ProductEvent event = ProductEvent.builder()
                .productId(updatedDto.getId())
                .name(updatedDto.getName())
                .category(updatedDto.getCategory())
                .price(updatedDto.getPrice())
                .status("UPDATED")
                .build();
        productProducer.sendProductEvent(event);

        return updatedDto;
    }

    @Transactional
    public void deleteProduct(Long id) {
        log.info("Deleting product with ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
        productRepository.delete(product);

             // Send Kafka event
        ProductEvent event = ProductEvent.builder()
                .productId(product.getId())
                .name(product.getName())
                .category(product.getCategory())
                .price(product.getPrice())
                .status("DELETED")
                .build();
        productProducer.sendProductEvent(event);
    }

    public List<ProductDto> getAllProducts(int page, int size, String sort) {
        return List.of();
    }
}
