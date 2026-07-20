package com.example.FoodDeliveryApp.service;

import com.example.FoodDeliveryApp.dto.DeliveryPartnerRequest;
import com.example.FoodDeliveryApp.dto.DeliveryPartnerResponse;

import java.util.List;

public interface DeliveryPartnerService {

    DeliveryPartnerResponse createDeliveryPartner(
            DeliveryPartnerRequest request);

    List<DeliveryPartnerResponse> getAllDeliveryPartners();

    DeliveryPartnerResponse getDeliveryPartnerById(
            Long id);

    void deleteDeliveryPartner(Long id);
}