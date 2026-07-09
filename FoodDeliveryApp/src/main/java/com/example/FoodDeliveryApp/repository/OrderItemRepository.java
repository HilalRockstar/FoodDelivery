package com.example.FoodDeliveryApp.repository;

import com.example.FoodDeliveryApp.entity.FoodOrder;
import com.example.FoodDeliveryApp.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByFoodOrder(FoodOrder foodOrder);
}