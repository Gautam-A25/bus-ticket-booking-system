package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.AuthDTO.LoginRequestDTO;
import com.busticket.busticketbooking.dto.AuthDTO.RegisterRequestDTO;
import com.busticket.busticketbooking.dto.AuthDTO.AuthResponseDTO;

/**
 * Service interface for user authentication operations.
 *
 * <p>Provides two operations:</p>
 * <ul>
 *   <li>{@link #register} — creates a new user account, hashes the password,
 *       and returns a JWT token for immediate use.</li>
 *   <li>{@link #login} — authenticates credentials against the database and
 *       returns a fresh JWT token on success.</li>
 * </ul>
 */
public interface AuthService {
    /** Registers a new user account and returns a JWT token. */
    AuthResponseDTO register(RegisterRequestDTO request);
    /** Authenticates an existing user and returns a JWT token. */
    AuthResponseDTO login(LoginRequestDTO request);
}