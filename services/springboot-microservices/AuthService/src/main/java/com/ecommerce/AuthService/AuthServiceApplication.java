package com.ecommerce.AuthService;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthServiceApplication {

	public static void main(String[] args) {
		// Load variables from .env file into System properties
		Dotenv dotenv = Dotenv.configure()
				.ignoreIfMissing()  // Ignores if .env is missing (to avoid errors)
				.load();  // Load the .env file

		dotenv.entries().forEach(entry ->
				System.setProperty(entry.getKey(), entry.getValue()) // Set the environment variables as system properties
		);

		// Run the Spring Boot application
		SpringApplication.run(AuthServiceApplication.class, args);
	}
}
