package com.busticket.busticketbooking.dto.AuthDTO;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object representing an API authentication request.
 * Contains user credentials for logging in and generating a JWT token.
 */
public class AuthRequest {

    /** The username of the user attempting to authenticate; required. */
    @NotBlank(message = "Username is required")
    private String username;

    /** The plain text password of the user; required. */
    @NotBlank(message = "Password is required")
    private String password;

    /**
     * Default no-argument constructor.
     */
    public AuthRequest() {
    }

    /**
     * Parameterized constructor to fully initialize the request object.
     *
     * @param username the username
     * @param password the password
     */
    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
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