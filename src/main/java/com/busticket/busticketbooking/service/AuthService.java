package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.authDTO.LoginRequestDTO;
import com.busticket.busticketbooking.dto.authDTO.RegisterRequestDTO;
import com.busticket.busticketbooking.dto.authDTO.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO register(RegisterRequestDTO request);
    AuthResponseDTO login(LoginRequestDTO request);
}