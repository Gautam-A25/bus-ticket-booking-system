package com.busticket.busticketbooking.dto.AuthDTO;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object representing a web UI login request.
 * Contains user credentials submitted via the Thymeleaf login form.
 */
public class LoginRequestDTO {

    /** The username submitted for login; required. */
    @NotBlank(message = "Username is required")
    private String username;

    /** The password submitted for login; required. */
    @NotBlank(message = "Password is required")
    private String password;

    /**
     * Default no-argument constructor.
     */
    public LoginRequestDTO() {
    }

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