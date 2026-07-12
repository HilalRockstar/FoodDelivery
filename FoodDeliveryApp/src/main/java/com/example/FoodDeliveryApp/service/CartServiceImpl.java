package com.example.FoodDeliveryApp.service;

import com.example.FoodDeliveryApp.entity.CartItem;
import com.example.FoodDeliveryApp.entity.MenuItem;
import com.example.FoodDeliveryApp.entity.User;
import com.example.FoodDeliveryApp.repository.CartItemRepository;
import com.example.FoodDeliveryApp.repository.MenuItemRepository;
import com.example.FoodDeliveryApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;

    private final UserRepository userRepository;

    private final MenuItemRepository menuItemRepository;

    @Override
    public boolean addToCart(Long menuItemId, String email) {

        // Find User
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // Find Menu Item
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() ->
                        new RuntimeException("Menu Item not found"));

        // ===============================
        // Restaurant Validation
        // ===============================

        List<CartItem> cartItems = cartItemRepository.findByUser(user);

        if (!cartItems.isEmpty()) {

            Long existingRestaurantId =
                    cartItems.get(0)
                            .getMenuItem()
                            .getRestaurant()
                            .getId();

            Long newRestaurantId =
                    menuItem.getRestaurant()
                            .getId();

            if (!existingRestaurantId.equals(newRestaurantId)) {

                return false;

            }
        }

        // ===============================
        // Existing Cart Item Check
        // ===============================

        Optional<CartItem> existingCartItem =
                cartItemRepository.findByUserAndMenuItem(user, menuItem);

        if (existingCartItem.isPresent()) {

            CartItem cartItem = existingCartItem.get();

            cartItem.setQuantity(cartItem.getQuantity() + 1);

            cartItemRepository.save(cartItem);

        } else {

            CartItem cartItem = CartItem.builder()
                    .user(user)
                    .menuItem(menuItem)
                    .quantity(1)
                    .build();

            cartItemRepository.save(cartItem);
        }

        return true;
    }

    @Override
    public List<CartItem> getMyCart(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return cartItemRepository.findByUser(user);
    }

    @Override
    public void removeFromCart(Long cartItemId) {

        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public void clearCart(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        List<CartItem> cartItems =
                cartItemRepository.findByUser(user);

        cartItemRepository.deleteAll(cartItems);
    }
    @Override
    public void increaseQuantity(Long cartItemId) {

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() ->
                        new RuntimeException("Cart Item not found"));

        cartItem.setQuantity(cartItem.getQuantity() + 1);

        cartItemRepository.save(cartItem);
    }
    @Override
    public void decreaseQuantity(Long cartItemId) {

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() ->
                        new RuntimeException("Cart Item not found"));

        if (cartItem.getQuantity() > 1) {

            cartItem.setQuantity(cartItem.getQuantity() - 1);

            cartItemRepository.save(cartItem);

        } else {

            cartItemRepository.delete(cartItem);
        }
    }
}