package com.busticket.busticketbooking.entity;

import jakarta.persistence.*;

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

    /** Unique login name; must not be null and is enforced as unique at the DB level. */
    @Column(nullable = false, unique = true)
    private String username;

    /** BCrypt-hashed password — never stored in plain text. */
    @Column(nullable = false)
    private String password;

    /** Role assigned to this user (e.g., "USER" or "ADMIN"). */
    @Column(nullable = false)
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