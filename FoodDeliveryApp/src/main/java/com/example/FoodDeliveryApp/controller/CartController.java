package com.example.FoodDeliveryApp.controller;
import com.example.FoodDeliveryApp.dto.CartRequest;
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

    @PostMapping("/add")
    public String addToCart(
            CartRequest request,
            Authentication authentication) {

        cartService.addToCart(
                request,
                authentication.getName());

        return "redirect:/user/cart";
    }

    @GetMapping
    public String viewCart(
            Authentication authentication,
            Model model) {

        model.addAttribute(
                "cartItems",
                cartService.getCartItems(
                        authentication.getName()));

        return "cart/list";
    }

    @GetMapping("/remove/{id}")
    public String removeCartItem(
            @PathVariable Long id) {

        cartService.removeCartItem(id);

        return "redirect:/user/cart";
    }
}
