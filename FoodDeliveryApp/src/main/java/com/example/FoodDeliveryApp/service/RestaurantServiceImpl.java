package com.example.FoodDeliveryApp.service;

import com.example.FoodDeliveryApp.dto.RestaurantRequest;
import com.example.FoodDeliveryApp.dto.RestaurantResponse;
import com.example.FoodDeliveryApp.entity.Restaurant;
import com.example.FoodDeliveryApp.repository.RestaurantRepository;
import com.example.FoodDeliveryApp.service.RestaurantService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl
        implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Override
    public RestaurantResponse createRestaurant(
            RestaurantRequest request) {

        Restaurant restaurant = Restaurant.builder()
                .name(request.getName())
                .description(request.getDescription())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .openingTime(request.getOpeningTime())
                .closingTime(request.getClosingTime())
                .active(true)
                .build();

        Restaurant savedRestaurant =
                restaurantRepository.save(restaurant);

        return mapToResponse(savedRestaurant);
    }

    @Override
    public List<RestaurantResponse> getAllRestaurants() {

        return restaurantRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RestaurantResponse getRestaurantById(
            Long id) {

        Restaurant restaurant =
                restaurantRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Restaurant not found"));

        return mapToResponse(restaurant);
    }

    @Override
    public void deleteRestaurant(Long id) {

        restaurantRepository.deleteById(id);
    }

    private RestaurantResponse mapToResponse(
            Restaurant restaurant) {

        return RestaurantResponse.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .description(restaurant.getDescription())
                .address(restaurant.getAddress())
                .phoneNumber(restaurant.getPhoneNumber())
                .openingTime(restaurant.getOpeningTime())
                .closingTime(restaurant.getClosingTime())
                .active(restaurant.isActive())
                .build();
    }
}