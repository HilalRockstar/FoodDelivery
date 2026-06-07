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

    private final UserService userService;

    @GetMapping("/register")
    public String registerPage(RegisterRequest request) {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @Valid RegisterRequest request,
            BindingResult result) {

        if(result.hasErrors()) {
            return "register";
        }

        userService.registerUser(request);

        return "redirect:/login";
    }
}