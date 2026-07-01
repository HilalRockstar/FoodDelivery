package com.example.FoodDeliveryApp.service;

import com.example.FoodDeliveryApp.entity.FoodOrder;

import java.util.List;

public interface FoodOrderService {

    void placeOrder(String email);
    List<FoodOrder> getMyOrders(String email);
}