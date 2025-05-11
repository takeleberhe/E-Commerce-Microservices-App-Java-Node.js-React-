package com.ecommerce.AuthService.Dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO implements Serializable {
    private static final long serialVersionUID = 1L; // Ensures compatibility during serialization

    private String email;
    private String password;
    private String username;

    @Override
    public String toString() {
        return "UserRegisterDTO{" +
                "email='" + email + '\'' +
                ", password='******'" + // Masked for security
                ", username='" + username + '\'' +
                '}';
    }
}
