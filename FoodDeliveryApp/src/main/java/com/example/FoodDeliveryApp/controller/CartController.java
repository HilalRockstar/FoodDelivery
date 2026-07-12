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

        boolean added =
                cartService.addToCart(menuItemId, email);

        if (!added) {

            return "redirect:/user/cart/confirm-clear?menuItemId="
                    + menuItemId;
        }

        return "redirect:/user/cart";
    }

    // Confirmation Page
    @GetMapping("/confirm-clear")
    public String confirmClear(
            @RequestParam Long menuItemId,
            Model model) {

        model.addAttribute("menuItemId", menuItemId);

        return "cart/confirm-clear";
    }

    // Clear Cart & Add New Item
    @GetMapping("/clear-and-add/{menuItemId}")
    public String clearAndAdd(
            @PathVariable Long menuItemId,
            Authentication authentication) {

        String email = authentication.getName();

        cartService.clearCart(email);

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

    // Increase Quantity
    @GetMapping("/increase/{cartItemId}")
    public String increaseQuantity(
            @PathVariable Long cartItemId) {

        cartService.increaseQuantity(cartItemId);

        return "redirect:/user/cart";
    }

    // Decrease Quantity
    @GetMapping("/decrease/{cartItemId}")
    public String decreaseQuantity(
            @PathVariable Long cartItemId) {

        cartService.decreaseQuantity(cartItemId);

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