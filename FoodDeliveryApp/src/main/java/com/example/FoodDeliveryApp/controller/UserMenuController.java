package com.example.FoodDeliveryApp.controller;

import com.example.FoodDeliveryApp.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class UserMenuController {

    private final MenuItemService menuItemService;

    @GetMapping("/user/menu/{restaurantId}")
    public String getMenuByRestaurant(
            @PathVariable Long restaurantId,
            Model model) {

        model.addAttribute(
                "menuItems",
                menuItemService.getMenuItemsByRestaurant(restaurantId));

        model.addAttribute(
                "restaurantId",
                restaurantId);

        return "user/menu";
    }
}