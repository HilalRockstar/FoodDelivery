package com.example.FoodDeliveryApp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartRequest {

    private Long menuItemId;

    private Integer quantity;
}