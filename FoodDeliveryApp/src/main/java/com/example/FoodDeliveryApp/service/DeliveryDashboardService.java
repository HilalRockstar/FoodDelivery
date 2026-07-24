package com.example.FoodDeliveryApp.service;

import com.example.FoodDeliveryApp.entity.FoodOrder;
import com.example.FoodDeliveryApp.enums.OrderStatus;

import java.util.List;

public interface DeliveryDashboardService {

    List<FoodOrder> getAssignedOrders(String email);

    FoodOrder getOrderById(Long orderId);

    void updateOrderStatus(Long orderId, OrderStatus status);

}