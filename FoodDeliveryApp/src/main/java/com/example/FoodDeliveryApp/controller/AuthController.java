package com.example.FoodDeliveryApp.controller;

import com.example.FoodDeliveryApp.dto.RegisterRequest;
import com.example.FoodDeliveryApp.service.UserService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private static final String FRONTEND_URL = System.getenv().getOrDefault(
            "FRONTEND_URL",
            "http://localhost:5173");

    private final UserService userService;

    @GetMapping("/register")
    public String registerPage(RegisterRequest request) {

        return "redirect:" + FRONTEND_URL + "/register";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "redirect:" + FRONTEND_URL + "/login";
    }

    @PostMapping("/register")
    public String registerUser(
            @Valid RegisterRequest request,
            BindingResult result) {

        if (result.hasErrors()) {
            return "redirect:" + FRONTEND_URL + "/register?error=validation";
        }

        userService.registerUser(request);

        return "redirect:" + FRONTEND_URL + "/login?registered";
    }
}