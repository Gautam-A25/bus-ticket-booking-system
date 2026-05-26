package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.dto.AuthDTO.AuthResponseDTO;
import com.busticket.busticketbooking.dto.AuthDTO.LoginRequestDTO;
import com.busticket.busticketbooking.dto.AuthDTO.RegisterRequestDTO;
import com.busticket.busticketbooking.entity.AppUser;
import com.busticket.busticketbooking.repo.AppUserRepo;
import com.busticket.busticketbooking.security.JwtService;
import com.busticket.busticketbooking.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Concrete implementation of {@link AuthService} that handles user registration
 * and login using Spring Security and JWT.
 *
 * <p><b>Registration flow:</b> validate uniqueness → hash password (BCrypt) → persist user → generate JWT.</p>
 * <p><b>Login flow:</b> delegate credentials to {@link AuthenticationManager} →
 *    fetch user from DB → generate JWT.</p>
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final AppUserRepo appUserRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public AuthServiceImpl(
            AppUserRepo appUserRepo,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtService jwtService
    ) {
        this.appUserRepo = appUserRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    /**
     * Registers a new user account.
     *
     * @param request username and plain-text password for the new account
     * @return an {@link AuthResponseDTO} containing a JWT token, username, and role
     * @throws IllegalArgumentException if the username is already taken
     */
    @Override
    public AuthResponseDTO register(RegisterRequestDTO request) {

        // Reject registration if the username is already in use
        if (appUserRepo.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        AppUser user = new AppUser();

        user.setUsername(request.getUsername());

        // Hash the password with BCrypt before persisting
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        // New accounts are assigned the default "USER" role
        user.setRole("USER");

        user.setEnabled(true);

        appUserRepo.save(user);

        // Load user details and issue a JWT for immediate use
        UserDetails userDetails =
                userDetailsService.loadUserByUsername(
                        user.getUsername()
                );

        String token = jwtService.generateToken(userDetails);

        return new AuthResponseDTO(
                token,
                user.getUsername(),
                user.getRole()
        );
    }

    /**
     * Authenticates a user with their username and password.
     *
     * @param request username and plain-text password to authenticate
     * @return an {@link AuthResponseDTO} containing a JWT token, username, and role
     * @throws org.springframework.security.core.AuthenticationException if credentials are invalid
     */
    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {

        // Delegates to Spring Security's AuthenticationManager — throws on bad credentials
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // Fetch the full AppUser to include role in the response
        AppUser user = appUserRepo.findByUsername(
                request.getUsername()
        ).orElseThrow(() ->
                new IllegalArgumentException("User not found")
        );

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(
                        user.getUsername()
                );

        // Generate and return a fresh JWT token
        String token = jwtService.generateToken(userDetails);

        return new AuthResponseDTO(
                token,
                user.getUsername(),
                user.getRole()
        );
    }
}