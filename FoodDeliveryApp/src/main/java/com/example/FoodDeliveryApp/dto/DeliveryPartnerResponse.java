package com.example.FoodDeliveryApp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryPartnerResponse {

    private Long id;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String vehicleNumber;

    private boolean available;

    private boolean enabled;
}