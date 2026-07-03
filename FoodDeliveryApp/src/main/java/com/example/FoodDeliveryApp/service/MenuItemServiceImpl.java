package com.example.FoodDeliveryApp.service;

import com.example.FoodDeliveryApp.dto.MenuItemRequest;
import com.example.FoodDeliveryApp.dto.MenuItemResponse;
import com.example.FoodDeliveryApp.entity.MenuItem;
import com.example.FoodDeliveryApp.entity.Restaurant;
import com.example.FoodDeliveryApp.repository.MenuItemRepository;
import com.example.FoodDeliveryApp.repository.RestaurantRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl
        implements MenuItemService {

    private final MenuItemRepository menuItemRepository;

    private final RestaurantRepository restaurantRepository;

    @Override
    public MenuItemResponse createMenuItem(
            MenuItemRequest request) {

        Restaurant restaurant =
                restaurantRepository.findById(
                                request.getRestaurantId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Restaurant not found"));

        MenuItem menuItem = MenuItem.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .available(true)
                .restaurant(restaurant)
                .build();

        MenuItem saved =
                menuItemRepository.save(menuItem);

        return mapToResponse(saved);
    }
    @Override
    public List<MenuItemResponse> getMenuItemsByRestaurant(
            Long restaurantId) {

        Restaurant restaurant = restaurantRepository
                .findById(restaurantId)
                .orElseThrow(() ->
                        new RuntimeException("Restaurant not found"));

        return menuItemRepository.findByRestaurant(restaurant)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public MenuItemResponse getMenuItemById(
            Long id) {

        MenuItem menuItem =
                menuItemRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Menu Item not found"));

        return mapToResponse(menuItem);
    }

    @Override
    public void deleteMenuItem(Long id) {

        menuItemRepository.deleteById(id);
    }

    private MenuItemResponse mapToResponse(
            MenuItem menuItem) {

        return MenuItemResponse.builder()
                .id(menuItem.getId())
                .name(menuItem.getName())
                .description(menuItem.getDescription())
                .price(menuItem.getPrice())
                .available(menuItem.isAvailable())
                .restaurantName(
                        menuItem.getRestaurant()
                                .getName())
                .build();
    }
}