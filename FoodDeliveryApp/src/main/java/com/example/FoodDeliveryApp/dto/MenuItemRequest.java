package com.example.FoodDeliveryApp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItemRequest {

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Double price;

    private Long restaurantId;
    private boolean available;
}