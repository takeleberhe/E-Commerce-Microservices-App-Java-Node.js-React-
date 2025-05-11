package com.ecommerce.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        // Disable CSRF for simplicity (not recommended for production without proper considerations)
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        // Allow unauthenticated access to endpoints under /auth/**
                        .pathMatchers("/api/auth/**").permitAll()
                        // Require authentication for all other endpoints
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                // Specify the URI of the JWK Set for verifying JWT tokens
                                .jwkSetUri("http://localhost:8080/oauth2/jwks")
                        )
                );

        // Build and return the SecurityWebFilterChain
        return http.build();
    }
}
