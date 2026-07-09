package com.example.FoodDeliveryApp.service;

import com.example.FoodDeliveryApp.entity.FoodOrder;
import com.example.FoodDeliveryApp.entity.OrderItem;

import java.util.List;

public interface FoodOrderService {

    void placeOrder(String email);
    List<FoodOrder> getMyOrders(String email);
    FoodOrder getOrderById(Long id);

    List<OrderItem> getOrderItems(Long orderId);
}