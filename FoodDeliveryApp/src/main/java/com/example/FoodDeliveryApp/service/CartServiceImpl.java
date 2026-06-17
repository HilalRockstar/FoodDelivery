package com.example.FoodDeliveryApp.service;

import com.example.FoodDeliveryApp.dto.CartRequest;
import com.example.FoodDeliveryApp.dto.CartResponse;
import com.example.FoodDeliveryApp.entity.CartItem;
import com.example.FoodDeliveryApp.entity.MenuItem;
import com.example.FoodDeliveryApp.entity.User;
import com.example.FoodDeliveryApp.repository.CartItemRepository;
import com.example.FoodDeliveryApp.repository.MenuItemRepository;
import com.example.FoodDeliveryApp.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl
        implements CartService {

    private final CartItemRepository cartItemRepository;

    private final UserRepository userRepository;

    private final MenuItemRepository menuItemRepository;

    @Override
    public CartResponse addToCart(
            CartRequest request,
            String email) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"));

        MenuItem menuItem =
                menuItemRepository.findById(
                                request.getMenuItemId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Menu Item not found"));

        CartItem cartItem =
                CartItem.builder()
                        .user(user)
                        .menuItem(menuItem)
                        .quantity(
                                request.getQuantity())
                        .build();

        CartItem saved =
                cartItemRepository.save(cartItem);

        return mapToResponse(saved);
    }

    @Override
    public List<CartResponse> getCartItems(
            String email) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"));

        return cartItemRepository
                .findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void removeCartItem(
            Long cartItemId) {

        cartItemRepository
                .deleteById(cartItemId);
    }

    private CartResponse mapToResponse(
            CartItem cartItem) {

        double total =
                cartItem.getMenuItem().getPrice()
                        * cartItem.getQuantity();

        return CartResponse.builder()
                .cartItemId(cartItem.getId())
                .menuItemName(
                        cartItem.getMenuItem()
                                .getName())
                .price(
                        cartItem.getMenuItem()
                                .getPrice())
                .quantity(
                        cartItem.getQuantity())
                .totalPrice(total)
                .build();
    }
}