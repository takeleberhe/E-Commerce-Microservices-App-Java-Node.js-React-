package com.order_service.order_service;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OrderServiceApplication {

	public static void main(String[] args) {
		// Load environment variables from the .env file before Spring Boot starts
		Dotenv dotenv = Dotenv.configure()
				.ignoreIfMissing()  // Ignore if .env is missing (to avoid errors)
				.load();  // Load the .env file

		// Set each key-value pair in the .env file as system properties
		dotenv.entries().forEach(entry ->
				System.setProperty(entry.getKey(), entry.getValue())
		);

		// Run the Spring Boot application
		SpringApplication.run(OrderServiceApplication.class, args);
	}
}
