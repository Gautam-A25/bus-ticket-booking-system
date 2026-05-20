package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.AuthDTO.LoginRequestDTO;
import com.busticket.busticketbooking.dto.AuthDTO.RegisterRequestDTO;
import com.busticket.busticketbooking.dto.AuthDTO.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO register(RegisterRequestDTO request);
    AuthResponseDTO login(LoginRequestDTO request);
}