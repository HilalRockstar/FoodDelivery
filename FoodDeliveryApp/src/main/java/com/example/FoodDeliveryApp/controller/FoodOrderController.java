package com.example.FoodDeliveryApp.controller;

import com.example.FoodDeliveryApp.entity.FoodOrder;
import com.example.FoodDeliveryApp.service.FoodOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;
@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class FoodOrderController {

    private final FoodOrderService foodOrderService;

    @PostMapping("/place")
    public String placeOrder(Authentication authentication) {

        foodOrderService.placeOrder(authentication.getName());

        return "redirect:/orders";
    }

    @GetMapping
    public String myOrders(Authentication authentication,
                           Model model) {

        String email = authentication.getName();

        model.addAttribute(
                "orders",
                foodOrderService.getMyOrders(email));

        return "user/orders";
    }
    @GetMapping("/{id}")
    public String orderDetails(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "order",
                foodOrderService.getOrderById(id));

        model.addAttribute(
                "orderItems",
                foodOrderService.getOrderItems(id));

        return "user/order-details";
    }
}