package com.busticket.busticketbooking.security;

import com.busticket.busticketbooking.entity.AppUser;
import com.busticket.busticketbooking.repo.AppUserRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Spring Security {@link UserDetailsService} implementation that loads users
 * from the {@code users} database table via {@link AppUserRepo}.
 *
 * <p>This service is used by both the JWT filter (for token validation) and the
 * DAO authentication provider (for form login). It maps each {@link AppUser}'s
 * {@code role} field to a Spring Security authority with the {@code ROLE_} prefix,
 * which is required for {@code @PreAuthorize} and {@code hasRole()} checks.</p>
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserRepo appUserRepo;

    public CustomUserDetailsService(AppUserRepo appUserRepo) {
        this.appUserRepo = appUserRepo;
    }

    /**
     * Loads a user from the database by their username.
     *
     * @param username the username submitted during login
     * @return a fully populated {@link UserDetails} object for Spring Security
     * @throws UsernameNotFoundException if no user with the given username exists
     */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        // Fetch the AppUser entity; throw 404-style exception if not found
        AppUser appUser = appUserRepo.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found: " + username
                        )
                );

        // Build a Spring Security User object with credentials, status flags, and a single role authority
        return new User(
                appUser.getUsername(),
                appUser.getPassword(),
                appUser.isEnabled(),
                true,   // accountNonExpired
                true,   // credentialsNonExpired
                true,   // accountNonLocked
                List.of(
                        // Role stored as "USER" or "ADMIN" in DB; prefixed with "ROLE_" for Spring Security
                        new SimpleGrantedAuthority(
                                "ROLE_" + appUser.getRole()
                        )
                )
        );
    }
}