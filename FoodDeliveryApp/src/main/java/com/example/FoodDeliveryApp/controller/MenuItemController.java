package com.example.FoodDeliveryApp.controller;

import com.example.FoodDeliveryApp.dto.MenuItemRequest;
import com.example.FoodDeliveryApp.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/menu")
@RequiredArgsConstructor
public class MenuItemController {

    private static final String FRONTEND_URL = System.getenv().getOrDefault(
            "FRONTEND_URL",
            "http://localhost:5173");

    private final MenuItemService menuItemService;

    // Show Menu Items of a Restaurant
    @GetMapping("/{restaurantId}")
    public String getMenuItemsByRestaurant(
            @PathVariable Long restaurantId,
            Model model) {
        return "redirect:" + FRONTEND_URL + "/admin/menu/" + restaurantId;
    }

    // Open Create Menu Page
    @GetMapping("/create/{restaurantId}")
    public String createMenuPage(
            @PathVariable Long restaurantId,
            Model model) {
        return "redirect:" + FRONTEND_URL + "/admin/menu/create/" + restaurantId;
    }

    // Save Menu Item
    @PostMapping("/create")
    public ResponseEntity<Void> createMenuItem(
            @ModelAttribute MenuItemRequest request) {

        menuItemService.createMenuItem(request);

        return ResponseEntity.ok().build();
    }

    // Delete Menu Item
    @GetMapping("/delete/{id}/{restaurantId}")
    public String deleteMenuItem(
            @PathVariable Long id,
            @PathVariable Long restaurantId) {

        menuItemService.deleteMenuItem(id);

        return "redirect:/admin/menu/" + restaurantId;
    }
}