package com.product_service.product_service.repository;

import com.product_service.product_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {
    // Custom query methods (if needed)
}
