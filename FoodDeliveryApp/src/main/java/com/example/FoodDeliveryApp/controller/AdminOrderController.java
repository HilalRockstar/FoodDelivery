package com.example.FoodDeliveryApp.controller;

import com.example.FoodDeliveryApp.enums.OrderStatus;
import com.example.FoodDeliveryApp.service.AdminOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    @GetMapping
    public String getAllOrders(Model model) {

        model.addAttribute(
                "orders",
                adminOrderService.getAllOrders());

        return "admin/orders";
    }

    @GetMapping("/{orderId}")
    public String getOrderDetails(
            @PathVariable Long orderId,
            Model model) {

        model.addAttribute(
                "order",
                adminOrderService.getOrderById(orderId));

        return "admin/order-details";
    }

    @PostMapping("/update-status")
    public String updateStatus(
            @RequestParam Long orderId,
            @RequestParam OrderStatus status) {

        adminOrderService.updateOrderStatus(
                orderId,
                status);

        return "redirect:/admin/orders";
    }
}