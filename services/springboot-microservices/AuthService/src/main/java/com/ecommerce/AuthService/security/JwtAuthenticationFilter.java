package com.ecommerce.AuthService.security;

import com.ecommerce.AuthService.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtAuthenticationFilter is a filter that intercepts HTTP requests to authenticate users
 * based on the JWT token passed in the Authorization header.
 * This filter is executed once per request to ensure that the security context is set up properly.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;  // Service to handle JWT generation, validation, and extraction
    private final UserDetailsService userDetailsService;  // Service to load user details from the database

    /**
     * Constructor that initializes the JwtAuthenticationFilter with the necessary services.
     *
     * @param jwtService Service for handling JWT-related logic.
     * @param userDetailsService Service to load user details by username.
     */
    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * This method is invoked to filter incoming HTTP requests for authentication.
     * It checks for the presence of a JWT in the Authorization header and validates it.
     * If the token is valid, it sets up the authentication context for the current request.
     *
     * @param request The HTTP request.
     * @param response The HTTP response.
     * @param filterChain The filter chain to pass the request along the pipeline.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException If an I/O error occurs during request processing.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Retrieve the Authorization header from the request
        String authHeader = request.getHeader("Authorization");

        // If there's no Authorization header or it doesn't start with "Bearer ", pass the request along
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the JWT token from the Authorization header (remove "Bearer " prefix)
        String token = authHeader.substring(7);

        // Extract the username from the token
        String username = jwtService.extractUsername(token);

        // If a username is present and no authentication is set in the context, proceed with authentication
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Load the user details using the extracted username
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // If the token is valid, set the authentication context with the user details
            if (jwtService.isTokenValid(token, userDetails)) {
                // Create a new authentication token
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // Set the request details for the authentication object
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication in the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // Pass the request along the filter chain (either authenticated or unauthenticated)
        filterChain.doFilter(request, response);
    }
}
