package com.example.FoodDeliveryApp.service;

import com.example.FoodDeliveryApp.dto.DeliveryPartnerRequest;
import com.example.FoodDeliveryApp.dto.DeliveryPartnerResponse;
import com.example.FoodDeliveryApp.entity.DeliveryPartner;
import com.example.FoodDeliveryApp.repository.DeliveryPartnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeliveryPartnerServiceImpl
        implements DeliveryPartnerService {

    private final DeliveryPartnerRepository deliveryPartnerRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public DeliveryPartnerResponse createDeliveryPartner(
            DeliveryPartnerRequest request) {

        DeliveryPartner deliveryPartner = DeliveryPartner.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .vehicleNumber(request.getVehicleNumber())
                .available(true)
                .enabled(true)
                .build();

        DeliveryPartner savedDeliveryPartner =
                deliveryPartnerRepository.save(deliveryPartner);

        return mapToResponse(savedDeliveryPartner);
    }

    @Override
    public List<DeliveryPartnerResponse> getAllDeliveryPartners() {

        return deliveryPartnerRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DeliveryPartnerResponse getDeliveryPartnerById(Long id) {

        DeliveryPartner deliveryPartner =
                deliveryPartnerRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Delivery Partner not found"));

        return mapToResponse(deliveryPartner);
    }

    @Override
    public void deleteDeliveryPartner(Long id) {

        deliveryPartnerRepository.deleteById(id);
    }

    private DeliveryPartnerResponse mapToResponse(
            DeliveryPartner deliveryPartner) {

        return DeliveryPartnerResponse.builder()
                .id(deliveryPartner.getId())
                .fullName(deliveryPartner.getFullName())
                .email(deliveryPartner.getEmail())
                .phoneNumber(deliveryPartner.getPhoneNumber())
                .vehicleNumber(deliveryPartner.getVehicleNumber())
                .available(deliveryPartner.isAvailable())
                .enabled(deliveryPartner.isEnabled())
                .build();
    }
}