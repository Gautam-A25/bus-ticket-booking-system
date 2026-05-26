package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.AuthDTO.AuthResponseDTO;
import com.busticket.busticketbooking.dto.AuthDTO.LoginRequestDTO;
import com.busticket.busticketbooking.dto.AuthDTO.RegisterRequestDTO;
import com.busticket.busticketbooking.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller that exposes public authentication endpoints.
 *
 * <p>All paths are prefixed with {@code /api/v1/auth} and are explicitly
 * permitted without a JWT token (configured in {@link com.busticket.busticketbooking.config.SecurityConfig}).</p>
 *
 * <p>On success, both endpoints return an {@link com.busticket.busticketbooking.dto.AuthDTO.AuthResponseDTO}
 * containing a JWT token that the client should include in subsequent requests.</p>
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * POST /api/v1/auth/register — registers a new user account.
     *
     * @return HTTP 201 Created with a JWT token, or 400 Bad Request if the username is taken
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
        } catch (IllegalArgumentException ex) {
            // Username already exists — return 400 without leaking internal details
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * POST /api/v1/auth/login — authenticates an existing user.
     *
     * @return HTTP 200 OK with a JWT token, or 401 Unauthorized if credentials are invalid
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        try {
            return ResponseEntity.ok(authService.login(request));
        } catch (Exception ex) {
            // Bad credentials — return 401 without leaking exception details
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}