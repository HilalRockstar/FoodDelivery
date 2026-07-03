package com.example.FoodDeliveryApp.service;

import com.example.FoodDeliveryApp.dto.MenuItemRequest;
import com.example.FoodDeliveryApp.dto.MenuItemResponse;

import java.util.List;

public interface MenuItemService {

    MenuItemResponse createMenuItem(
            MenuItemRequest request);

    List<MenuItemResponse>getMenuItemsByRestaurant(Long restaurantId);

    MenuItemResponse getMenuItemById(
            Long id);

    void deleteMenuItem(Long id);
}