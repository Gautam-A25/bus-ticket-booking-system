package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.dto.authDTO.AuthResponseDTO;
import com.busticket.busticketbooking.dto.authDTO.LoginRequestDTO;
import com.busticket.busticketbooking.dto.authDTO.RegisterRequestDTO;
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

    @Override
    public AuthResponseDTO register(RegisterRequestDTO request) {

        if (appUserRepo.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        AppUser user = new AppUser();

        user.setUsername(request.getUsername());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setRole("USER");

        user.setEnabled(true);

        appUserRepo.save(user);

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

    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        AppUser user = appUserRepo.findByUsername(
                request.getUsername()
        ).orElseThrow(() ->
                new IllegalArgumentException("User not found")
        );

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
}