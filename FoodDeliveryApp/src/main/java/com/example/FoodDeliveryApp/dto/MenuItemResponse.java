package com.example.FoodDeliveryApp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItemResponse {

    private Long id;

    private String name;

    private String description;

    private Double price;

    private boolean available;

    private String restaurantName;
}