package com.ecommerce.AuthService.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@ToString(exclude = "jwtToken")  // Masking the JWT token in the toString output
public class AuthResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L; // Ensures compatibility during serialization

    private final String jwtToken;
}
