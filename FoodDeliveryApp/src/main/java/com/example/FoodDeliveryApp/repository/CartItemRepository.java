package com.example.FoodDeliveryApp.repository;

import com.example.FoodDeliveryApp.entity.CartItem;
import com.example.FoodDeliveryApp.entity.MenuItem;
import com.example.FoodDeliveryApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository
        extends JpaRepository<CartItem, Long> {

    // Get all cart items of a user
    List<CartItem> findByUser(User user);

    // Check whether the same menu item already exists in the user's cart
    Optional<CartItem> findByUserAndMenuItem(
            User user,
            MenuItem menuItem
    );
    void deleteByUser(User user);

}