package com.example.FoodDeliveryApp.repository;

import com.example.FoodDeliveryApp.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}