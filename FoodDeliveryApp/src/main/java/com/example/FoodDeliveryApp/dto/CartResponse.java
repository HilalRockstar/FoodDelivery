package com.example.FoodDeliveryApp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponse {

    private Long cartItemId;

    private String menuItemName;

    private Double price;

    private Integer quantity;

    private Double totalPrice;
}