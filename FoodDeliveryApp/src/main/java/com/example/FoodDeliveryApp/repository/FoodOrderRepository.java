package com.example.FoodDeliveryApp.repository;

import com.example.FoodDeliveryApp.entity.FoodOrder;
import com.example.FoodDeliveryApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodOrderRepository
        extends JpaRepository<FoodOrder, Long> {

    // User Orders
    List<FoodOrder> findByUserOrderByIdDesc(User user);

    // Admin - All Orders
    List<FoodOrder> findAllByOrderByIdDesc();
}