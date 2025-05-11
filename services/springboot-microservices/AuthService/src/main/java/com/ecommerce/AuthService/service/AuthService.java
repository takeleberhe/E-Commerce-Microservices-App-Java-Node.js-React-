package com.ecommerce.AuthService.service;

import com.ecommerce.AuthService.Dto.UserDTO;
import com.ecommerce.AuthService.entity.User;
import com.ecommerce.AuthService.repository.UserRepository;
import com.ecommerce.AuthService.exception.InvalidCredentialsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Authenticates the user and generates a JWT token if the credentials are valid.
     *
     * @param userDTO The UserDTO containing the user's credentials.
     * @return ResponseEntity containing the user details and JWT token if authentication is successful.
     * @throws InvalidCredentialsException if authentication fails.
     */
    public ResponseEntity<AuthResponse> loginUser(UserDTO userDTO) throws InvalidCredentialsException {
        User user = validateUserCredentials(userDTO);

        // Authenticate the user using AuthenticationManager
        Authentication authentication = authenticateUser(userDTO);

        // Generate the JWT token
        String token = jwtService.generateToken((UserDetails) authentication.getPrincipal());

        log.info("User {} logged in successfully.", user.getUsername());

        // Return response containing the user details and token
        return ResponseEntity.ok(new AuthResponse(user, token));

    }

    /**
     * Validates the user credentials by checking if the user exists and the password matches.
     *
     * @param userDTO the DTO containing the user's credentials
     * @return the validated User entity
     * @throws InvalidCredentialsException if the user is not found or the password is invalid
     */
    private User validateUserCredentials(UserDTO userDTO) throws InvalidCredentialsException {
        User user = userRepository.findByUsername(userDTO.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or email."));

        if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid password.");
        }

        return user;
    }

    /**
     * Authenticates the user using the AuthenticationManager.
     *
     * @param userDTO the DTO containing the user's credentials
     * @return the authenticated user details
     */
    private Authentication authenticateUser(UserDTO userDTO) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword());
        return authenticationManager.authenticate(authenticationToken);
    }

    /**
     * Encapsulates the user details and JWT token in the response.
     */
    public static class AuthResponse {
        private final String username;
        private final String email;
        private final String token;

        public AuthResponse(User user, String token) {
            this.username = user.getUsername();
            this.email = user.getEmail();
            this.token = token;
        }

        public String getUsername() {
            return username;
        }

        public String getEmail() {
            return email;
        }

        public String getToken() {
            return token;
        }
    }
}
