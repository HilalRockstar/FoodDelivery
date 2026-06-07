package com.example.FoodDeliveryApp.service;

import com.example.FoodDeliveryApp.dto.RegisterRequest;
import com.example.FoodDeliveryApp.entity.User;
import com.example.FoodDeliveryApp.enums.roles;
import com.example.FoodDeliveryApp.repository.UserRepository;
import com.example.FoodDeliveryApp.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(RegisterRequest request) {

        if(userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                .phoneNumber(request.getPhoneNumber())
                .role(roles.USER)
                .enabled(true)
                .build();

        userRepository.save(user);
    }
}