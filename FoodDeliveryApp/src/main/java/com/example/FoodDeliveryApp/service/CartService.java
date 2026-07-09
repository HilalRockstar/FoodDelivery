package com.example.FoodDeliveryApp.service;

import com.example.FoodDeliveryApp.entity.CartItem;

import java.util.List;

public interface CartService {

    void addToCart(
            Long menuItemId,
            String email);

    List<CartItem> getMyCart(
            String email);

    void removeFromCart(
            Long cartItemId);

    void clearCart(
            String email);
}