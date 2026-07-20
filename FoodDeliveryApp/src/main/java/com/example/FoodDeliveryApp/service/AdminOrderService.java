package com.example.FoodDeliveryApp.service;

import com.example.FoodDeliveryApp.entity.FoodOrder;
import com.example.FoodDeliveryApp.enums.OrderStatus;

import java.util.List;

public interface AdminOrderService {

    List<FoodOrder> getAllOrders();

    FoodOrder getOrderById(Long orderId);

    void updateOrderStatus(Long orderId,
                           OrderStatus status);
    void assignDeliveryPartner(
            Long orderId,
            Long deliveryPartnerId);
}