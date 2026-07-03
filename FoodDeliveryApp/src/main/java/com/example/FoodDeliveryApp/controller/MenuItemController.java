package com.example.FoodDeliveryApp.controller;

import com.example.FoodDeliveryApp.dto.MenuItemRequest;
import com.example.FoodDeliveryApp.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/menu")
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;

    // Show Menu Items of a Restaurant
    @GetMapping("/{restaurantId}")
    public String getMenuItemsByRestaurant(
            @PathVariable Long restaurantId,
            Model model) {

        model.addAttribute(
                "menuItems",
                menuItemService.getMenuItemsByRestaurant(restaurantId));

        model.addAttribute(
                "restaurantId",
                restaurantId);

        return "menu/list";
    }

    // Open Create Menu Page
    @GetMapping("/create/{restaurantId}")
    public String createMenuPage(
            @PathVariable Long restaurantId,
            Model model) {

        MenuItemRequest request = new MenuItemRequest();
        request.setRestaurantId(restaurantId);

        model.addAttribute("request", request);

        return "menu/create";
    }

    // Save Menu Item
    @PostMapping("/create")
    public String createMenuItem(
            @ModelAttribute MenuItemRequest request) {

        menuItemService.createMenuItem(request);

        return "redirect:/admin/menu/" + request.getRestaurantId();
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