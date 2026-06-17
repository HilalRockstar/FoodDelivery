package com.example.FoodDeliveryApp.service;

import com.example.FoodDeliveryApp.dto.RestaurantRequest;
import com.example.FoodDeliveryApp.dto.RestaurantResponse;

import java.util.List;

public interface RestaurantService {

    RestaurantResponse createRestaurant(
            RestaurantRequest request);

    List<RestaurantResponse> getAllRestaurants();

    RestaurantResponse getRestaurantById(
            Long id);

    void deleteRestaurant(Long id);
}