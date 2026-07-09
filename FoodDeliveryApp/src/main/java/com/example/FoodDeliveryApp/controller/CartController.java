package com.example.FoodDeliveryApp.controller;

import com.example.FoodDeliveryApp.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // View Cart
    @GetMapping
    public String viewCart(
            Authentication authentication,
            Model model) {

        String email = authentication.getName();

        model.addAttribute(
                "cartItems",
                cartService.getMyCart(email));

        return "cart/list";
    }

    // Add To Cart
    @GetMapping("/add/{menuItemId}")
    public String addToCart(
            @PathVariable Long menuItemId,
            Authentication authentication) {

        String email = authentication.getName();

        cartService.addToCart(menuItemId, email);

        return "redirect:/user/cart";
    }

    // Remove Cart Item
    @GetMapping("/remove/{cartItemId}")
    public String removeCartItem(
            @PathVariable Long cartItemId) {

        cartService.removeFromCart(cartItemId);

        return "redirect:/user/cart";
    }

    // Clear Cart
    @GetMapping("/clear")
    public String clearCart(
            Authentication authentication) {

        String email = authentication.getName();

        cartService.clearCart(email);

        return "redirect:/user/cart";
    }
}