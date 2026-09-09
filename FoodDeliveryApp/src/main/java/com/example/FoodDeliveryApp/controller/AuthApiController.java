package com.example.FoodDeliveryApp.controller;

import com.example.FoodDeliveryApp.dto.AuthResponse;
import com.example.FoodDeliveryApp.dto.LoginRequest;
import com.example.FoodDeliveryApp.security.CustomUserDetailsService;
import com.example.FoodDeliveryApp.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AuthApiController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;

    public AuthApiController(
            AuthenticationManager authenticationManager,
            CustomUserDetailsService customUserDetailsService,
            JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()));

            UserDetails userDetails = customUserDetailsService
                    .loadUserByUsername(loginRequest.getEmail());

            String token = jwtUtil.generateToken(userDetails);

            AuthResponse response = new AuthResponse();
            response.setToken(token);
            response.setEmail(userDetails.getUsername());
            response.setRole(userDetails.getAuthorities().stream()
                    .findFirst()
                    .map(authority -> authority.getAuthority().replace("ROLE_", ""))
                    .orElse("USER"));

            return ResponseEntity.ok(response);
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }
}
