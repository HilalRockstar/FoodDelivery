package com.example.FoodDeliveryApp.controller;

import com.example.FoodDeliveryApp.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserRestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/user/restaurants")
    public String getAllRestaurants(Model model) {

        var restaurants = restaurantService.getAllRestaurants();

        model.addAttribute("restaurants", restaurants);

        return "user/restaurants";

    }

}