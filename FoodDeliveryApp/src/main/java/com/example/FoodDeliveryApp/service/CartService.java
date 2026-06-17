package com.example.FoodDeliveryApp.service;

import com.example.FoodDeliveryApp.dto.CartRequest;
import com.example.FoodDeliveryApp.dto.CartResponse;

import java.util.List;

public interface CartService {

    CartResponse addToCart(
            CartRequest request,
            String email);

    List<CartResponse> getCartItems(
            String email);

    void removeCartItem(Long cartItemId);
}