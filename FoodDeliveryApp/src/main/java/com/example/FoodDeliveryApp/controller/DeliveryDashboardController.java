package com.example.FoodDeliveryApp.controller;

import com.example.FoodDeliveryApp.enums.OrderStatus;
import com.example.FoodDeliveryApp.service.DeliveryDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/delivery")
public class DeliveryDashboardController {

    private final DeliveryDashboardService deliveryDashboardService;

    @GetMapping("/dashboard")
    public String dashboard(
            Authentication authentication,
            Model model) {

        String email = authentication.getName();

        model.addAttribute(
                "orders",
                deliveryDashboardService.getAssignedOrders(email));

        return "delivery/dashboard";
    }

    @GetMapping("/order/{orderId}")
    public String viewAssignedOrder(
            @PathVariable Long orderId,
            Model model) {

        model.addAttribute(
                "order",
                deliveryDashboardService.getOrderById(orderId));

        return "delivery/order-details";
    }
    @PostMapping("/update-status")
    public String updateStatus(
            @RequestParam Long orderId,
            @RequestParam OrderStatus status) {

        deliveryDashboardService.updateOrderStatus(
                orderId,
                status);

        return "redirect:/delivery/order/" + orderId;
    }
}