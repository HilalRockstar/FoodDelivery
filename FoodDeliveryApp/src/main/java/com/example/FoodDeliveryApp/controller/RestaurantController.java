package com.example.FoodDeliveryApp.controller;

import com.example.FoodDeliveryApp.dto.RestaurantRequest;
import com.example.FoodDeliveryApp.service.RestaurantService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private static final String FRONTEND_URL = System.getenv().getOrDefault(
            "FRONTEND_URL",
            "http://localhost:5173");

    private final RestaurantService restaurantService;

    @GetMapping
    public String getAllRestaurants(Model model) {
        return "redirect:" + FRONTEND_URL + "/admin/restaurants";
    }

    @GetMapping("/create")
    public String createRestaurantPage(
            RestaurantRequest request) {
        return "redirect:" + FRONTEND_URL + "/admin/restaurants/create";
    }

    @PostMapping("/create")
    public String createRestaurant(
            @Valid RestaurantRequest request,
            BindingResult result) {

        if (result.hasErrors()) {
            return "restaurant/create";
        }

        restaurantService.createRestaurant(request);

        return "redirect:/admin/restaurants";
    }

    @GetMapping("/{id}")
    public String getRestaurantById(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "restaurant",
                restaurantService.getRestaurantById(id));

        return "restaurant/view";
    }

    @GetMapping("/delete/{id}")
    public String deleteRestaurant(
            @PathVariable Long id) {

        restaurantService.deleteRestaurant(id);

        return "redirect:/admin/restaurants";
    }
}