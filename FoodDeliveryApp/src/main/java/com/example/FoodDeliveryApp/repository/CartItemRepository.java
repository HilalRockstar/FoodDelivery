package com.example.FoodDeliveryApp.repository;

import com.example.FoodDeliveryApp.entity.CartItem;
import com.example.FoodDeliveryApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository
        extends JpaRepository<CartItem, Long> {

    List<CartItem> findByUser(User user);

    void deleteByUser(User user);
}