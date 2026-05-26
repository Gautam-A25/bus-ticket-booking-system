package com.busticket.busticketbooking.repo;

import com.busticket.busticketbooking.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository interface for {@link AppUser} database operations.
 *
 * <p>Handles user lookup by username and uniqueness checks during signup/registration flows.</p>
 */
@Repository
public interface AppUserRepo extends JpaRepository<AppUser, Integer> {
    /**
     * Looks up an application user by their unique username.
     *
     * @param username the username to search for
     * @return an {@link Optional} containing the AppUser if found, or empty otherwise
     */
    Optional<AppUser> findByUsername(String username);

    /**
     * Checks if a user already exists with the given username.
     *
     * @param username the username to check
     * @return true if a user exists with the username, false otherwise
     */
    boolean existsByUsername(String username);
}