package com.ecommerce.AuthService.service;

import com.ecommerce.AuthService.entity.User;
import com.ecommerce.AuthService.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Custom implementation of {@link UserDetailsService} to load user-specific data.
 * This service retrieves user details from the database based on the provided username.
 * It maps the user's roles to Spring Security authorities to handle authorization.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Constructor for CustomUserDetailsService that injects the {@link UserRepository}.
     *
     * @param userRepository The repository used to interact with the user database.
     */
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads the user details by username from the repository.
     * The method retrieves the user and constructs a Spring Security {@link UserDetails} object.
     *
     * @param username The username of the user to be loaded.
     * @return The {@link UserDetails} object containing user information and authorities.
     * @throws UsernameNotFoundException If no user with the given username exists.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch the user from the repository based on the provided username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Map the user's roles to authorities and return the UserDetails object
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                        .collect(Collectors.toList())
        );
    }
}
