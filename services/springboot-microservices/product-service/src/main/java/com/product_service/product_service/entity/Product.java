package com.product_service.product_service.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrementing primary key
    private Long id;

    @Column(nullable = false, unique = true) // Product name must be unique and not null
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal price; // Use BigDecimal for monetary values to avoid floating-point errors

    @Column(nullable = false)
    private Integer stock; // Represents available stock for the product

    @Column(nullable = false)
    private String category; // Category to which the product belongs

    @Column(name = "image_url")
    private String imageUrl; // URL of the product image
}
