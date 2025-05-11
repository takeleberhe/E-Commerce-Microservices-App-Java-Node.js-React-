package com.product_service.product_service;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class for the Product Service application.
 * It serves as the entry point for the Spring Boot application.
 */
@SpringBootApplication
public class ProductServiceApplication {

	/**
	 * The main method, which launches the Spring Boot application.
	 *
	 * @param args Command-line arguments passed during application startup.
	 */
	public static void main(String[] args) {
		// Load environment variables from the .env file before Spring Boot starts
		Dotenv dotenv = Dotenv.configure()
				.ignoreIfMissing()  // Ignore if .env is missing to avoid errors
				.load();  // Load the .env file

		// Set each key-value pair from the .env file as system properties
		dotenv.entries().forEach(entry ->
				System.setProperty(entry.getKey(), entry.getValue())
		);

		// Start the Spring Boot application
		SpringApplication.run(ProductServiceApplication.class, args);
	}
}
