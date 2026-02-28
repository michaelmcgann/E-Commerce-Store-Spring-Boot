package com.sbeccommerce.ecommercestore.security.DTO.login;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequest {

    @NotBlank
    @Size(min = 2, message = "Username should be longer than 2 characters")
    private String username;

    @NotBlank
    @Size(min = 5, message = "Password should be longer than 5 characters")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
