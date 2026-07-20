package com.example.FoodDeliveryApp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryPartnerRequest {

    private String fullName;

    private String email;

    private String password;

    private String phoneNumber;

    private String vehicleNumber;
}