package com.example.FoodDeliveryApp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantResponse {

    private Long id;

    private String name;

    private String description;

    private String address;

    private String phoneNumber;

    private String openingTime;

    private String closingTime;

    private boolean active;
}