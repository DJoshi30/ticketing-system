package com.dhara.ticketing_system.controller;

import com.dhara.ticketing_system.dto.LoginRequest;
import com.dhara.ticketing_system.dto.LoginResponse;
import com.dhara.ticketing_system.entity.User;
import com.dhara.ticketing_system.exception.ResourceNotFoundException;
import com.dhara.ticketing_system.repository.UserRepository;
import com.dhara.ticketing_system.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return new LoginResponse(token);
    }
}