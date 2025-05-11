package com.ecommerce.AuthService.controller;

import com.ecommerce.AuthService.Dto.UserResponseDTO;
import com.ecommerce.AuthService.Dto.UserDTO;
import com.ecommerce.AuthService.entity.User;
import com.ecommerce.AuthService.exception.InvalidCredentialsException;
import com.ecommerce.AuthService.service.UserService;
import com.ecommerce.AuthService.service.AuthService;
import com.ecommerce.AuthService.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final UserMapper userMapper;

    /**
     * Registers a new user in the system.
     *
     * @param userDTO the UserDTO containing the user information
     * @return ResponseEntity containing a success message and the registered user details
     */
    @Operation(summary = "Register a new user", description = "Registers a new user and returns a success message along with the user's details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User successfully registered"),
            @ApiResponse(responseCode = "400", description = "Bad Request, if the user data is invalid"),
    })
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@Valid @RequestBody UserDTO userDTO) {
        log.info("Attempting to register user with username: {}", userDTO.getUsername());

        User user = userMapper.userDTOToUser(userDTO);
        UserResponseDTO registeredUser = userService.register(user);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "User registered successfully!");
        responseBody.put("username", registeredUser.getUsername());
        responseBody.put("user", registeredUser);

        log.info("User registered successfully with username: {}", registeredUser.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    /**
     * Authenticates the user and generates a JWT token.
     *
     * @param userDTO the UserDTO containing the username/email and password
     * @return ResponseEntity containing the user details and JWT token if authentication is successful
     */
    @Operation(summary = "Login a user", description = "Authenticates the user and returns a JWT token if credentials are valid.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful, JWT token generated"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
    })
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) throws InvalidCredentialsException {
        log.info("Attempting login for user: {}", userDTO.getUsername());
        return authService.loginUser(userDTO);
    }
}
