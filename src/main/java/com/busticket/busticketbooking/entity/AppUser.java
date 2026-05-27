package com.busticket.busticketbooking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * JPA entity representing the {@code users} table in the database.
 *
 * <p>Stores authentication credentials for the application's admin/operator accounts.
 * End-user (customer) data is stored separately in the {@code customers} table via
 * {@link com.busticket.busticketbooking.entity.Customer}.</p>
 *
 * <p>The {@code role} field currently supports {@code "USER"} and {@code "ADMIN"} values.
 * Spring Security prepends {@code ROLE_} automatically when checking authorities.</p>
 */
@Entity
@Table(name = "users")
public class AppUser {

    /** Auto-generated primary key for the user record. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Username is required")
    @Size(max = 50, message = "Username must not exceed 50 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    private String password;

    @NotBlank(message = "Role is required")
    @Size(max = 20, message = "Role must not exceed 20 characters")
    private String role;

    /** Whether this account is active; disabled accounts cannot log in. Defaults to {@code true}. */
    @Column(nullable = false)
    private boolean enabled = true;

    public AppUser() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}