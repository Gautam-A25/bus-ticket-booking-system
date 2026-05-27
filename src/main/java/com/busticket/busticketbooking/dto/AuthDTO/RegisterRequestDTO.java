package com.busticket.busticketbooking.dto.AuthDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object representing a registration request.
 * Contains user credentials submitted to register a new user in the system.
 */
public class RegisterRequestDTO {

    /** The desired username for registration; required. */
    @NotBlank(message = "Username is required")
    private String username;

    /** The desired password for registration; required, minimum 4 characters. */
    @NotBlank(message = "Password is required")
    @Size(min = 4, message = "Password must be at least 4 characters")
    private String password;

    /**
     * Default no-argument constructor.
     */
    public RegisterRequestDTO() {
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