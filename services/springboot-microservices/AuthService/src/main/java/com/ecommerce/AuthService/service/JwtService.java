package com.ecommerce.AuthService.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service to handle JWT (JSON Web Token) generation and validation.
 * This class provides methods to extract information from the JWT and
 * generate a new JWT token based on user details.
 */
@Service
public class JwtService {

    // Secret key used for signing the JWTs. Replace with a secure key in production.
    private static final String SECRET_KEY = "your_secret_key"; // TODO: Replace with a secure key

    /**
     * Extracts the username from the JWT token.
     *
     * @param token the JWT token.
     * @return the username extracted from the token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the expiration date of the JWT token.
     *
     * @param token the JWT token.
     * @return the expiration date.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts a specific claim from the JWT token.
     *
     * @param token the JWT token.
     * @param claimsResolver function to extract the claim.
     * @param <T> the type of the claim.
     * @return the claim value extracted from the token.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token the JWT token.
     * @return the claims from the token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Checks if the JWT token is expired.
     *
     * @param token the JWT token.
     * @return true if the token is expired, false otherwise.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Validates the JWT token.
     *
     * @param token the JWT token.
     * @param userDetails the user details to validate the token against.
     * @return true if the token is valid, false otherwise.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        // Checks if the username matches and the token is not expired.
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Generates a JWT token based on user details.
     *
     * @param userDetails the user details.
     * @return the generated JWT token.
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a JWT token with additional claims.
     *
     * @param extraClaims additional claims to include in the token.
     * @param userDetails the user details.
     * @return the generated JWT token.
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        // Builds the JWT token with the provided claims, subject, and expiration.
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername()) // The subject is the username of the user.
                .setIssuedAt(new Date(System.currentTimeMillis())) // Token issued at the current time.
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours validity.
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // Signs the token using the specified algorithm and secret key.
                .compact();
    }
}
