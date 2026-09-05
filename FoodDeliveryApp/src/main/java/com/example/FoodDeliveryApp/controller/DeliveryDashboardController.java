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

        private static final String FRONTEND_URL = System.getenv().getOrDefault(
                        "FRONTEND_URL",
                        "http://localhost:5173");

        private final DeliveryDashboardService deliveryDashboardService;

        @GetMapping("/dashboard")
        public String dashboard(
                        Authentication authentication,
                        Model model) {
                return "redirect:" + FRONTEND_URL + "/delivery/orders";
        }

        @GetMapping("/order/{orderId}")
        public String viewAssignedOrder(
                        @PathVariable Long orderId,
                        Model model) {
                return "redirect:" + FRONTEND_URL + "/delivery/order/" + orderId;
        }

        @PostMapping("/update-status")
        public String updateStatus(
                        @RequestParam Long orderId,
                        @RequestParam OrderStatus status) {

                deliveryDashboardService.updateOrderStatus(
                                orderId,
                                status);

                return "redirect:" + FRONTEND_URL + "/delivery/order/" + orderId;
        }
}