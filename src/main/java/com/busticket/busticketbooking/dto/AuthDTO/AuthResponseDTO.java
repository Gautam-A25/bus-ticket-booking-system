package com.busticket.busticketbooking.dto.AuthDTO;

/**
 * Data Transfer Object representing an authentication response.
 * Returns the generated JWT token along with username and role details.
 */
public class AuthResponseDTO {

    /** The generated JWT token. */
    private String token;

    /** The authenticated username. */
    private String username;

    /** The security role of the authenticated user. */
    private String role;

    /**
     * Default no-argument constructor.
     */
    public AuthResponseDTO() {
    }

    /**
     * Parameterized constructor to fully initialize the response DTO.
     *
     * @param token    the generated JWT token
     * @param username the authenticated username
     * @param role     the user security role
     */
    public AuthResponseDTO(String token, String username, String role) {
        this.token = token;
        this.username = username;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
