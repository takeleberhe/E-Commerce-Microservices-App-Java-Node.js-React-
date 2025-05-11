package com.ecommerce.AuthService.service;

import com.ecommerce.AuthService.Dto.AuthResponseDTO;
import com.ecommerce.AuthService.Dto.UserLoginDTO;
import com.ecommerce.AuthService.Dto.UserResponseDTO;
import com.ecommerce.AuthService.entity.Role;
import com.ecommerce.AuthService.entity.User;
import com.ecommerce.AuthService.repository.RoleRepository;
import com.ecommerce.AuthService.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Service class responsible for handling user-related operations such as
 * registration, login, and user data management.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    /**
     * Constructor to initialize required dependencies.
     *
     * @param userRepository the user repository for data operations.
     * @param roleRepository the role repository to manage user roles.
     * @param passwordEncoder the password encoder for encoding passwords.
     * @param authenticationManager the authentication manager for handling authentication.
     * @param jwtService the JWT service to generate and validate JWT tokens.
     */
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    /**
     * Registers a new user with an encoded password and assigns a default role.
     *
     * @param request the user registration request containing email, username, and password.
     * @return UserResponseDTO containing the user's details.
     * @throws IllegalArgumentException if the email is already in use or the role is not found.
     */
    public UserResponseDTO register(User request) {
        // Check if the email is already in use
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already in use.");
        }

        // Encode the password using the password encoder
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // Load the "ROLE_USER" role from the database or throw an exception if not found
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        // Create a new user entity and assign values
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(encodedPassword);
        user.setRoles(Collections.singleton(userRole)); // Assign the default role

        // Save the user to the database
        User savedUser = userRepository.save(user);

        // Ensure the user was saved successfully and the ID is generated
        if (savedUser.getId() == null) {
            throw new RuntimeException("User registration failed, ID not generated.");
        }

        // Return a DTO containing user details
        return new UserResponseDTO(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());
    }

    /**
     * Authenticates the user by verifying their credentials and generates a JWT token.
     *
     * @param request the login request containing the user's email and password.
     * @return AuthResponseDTO containing the JWT token.
     * @throws IllegalArgumentException if the credentials are invalid.
     */
    public AuthResponseDTO login(UserLoginDTO request) {
        // Authenticate the user credentials using the AuthenticationManager
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // Fetch the user from the database based on the email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        // Generate a JWT token for the authenticated user
        String jwtToken = jwtService.generateToken((UserDetails) user);

        // Return the JWT token wrapped in a DTO
        return new AuthResponseDTO(jwtToken);
    }
}
