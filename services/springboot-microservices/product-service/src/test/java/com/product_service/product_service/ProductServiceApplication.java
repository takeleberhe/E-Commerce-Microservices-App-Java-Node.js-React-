package com.product_service.product_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point for the Product Service application.
 */
@SpringBootApplication
public class ProductServiceApplication {

    public static void main(String[] args) {
        // Bootstraps the Spring Boot application
        SpringApplication.run(ProductServiceApplication.class, args);
        System.out.println("Product Service is running...");
    }
}
