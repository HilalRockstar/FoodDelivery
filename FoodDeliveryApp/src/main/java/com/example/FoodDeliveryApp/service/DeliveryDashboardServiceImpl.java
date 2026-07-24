package com.example.FoodDeliveryApp.service;

import com.example.FoodDeliveryApp.entity.DeliveryPartner;
import com.example.FoodDeliveryApp.entity.FoodOrder;
import com.example.FoodDeliveryApp.enums.OrderStatus;
import com.example.FoodDeliveryApp.repository.DeliveryPartnerRepository;
import com.example.FoodDeliveryApp.repository.FoodOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryDashboardServiceImpl
        implements DeliveryDashboardService {

    private final DeliveryPartnerRepository deliveryPartnerRepository;

    private final FoodOrderRepository foodOrderRepository;

    @Override
    public List<FoodOrder> getAssignedOrders(String email) {

        DeliveryPartner partner =
                deliveryPartnerRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException("Delivery Partner not found"));

        return foodOrderRepository
                .findByDeliveryPartnerOrderByIdDesc(partner);
    }
    @Override
    public FoodOrder getOrderById(Long orderId) {

        return foodOrderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));
    }

    @Override
    public void updateOrderStatus(Long orderId,
                                  OrderStatus status) {

        FoodOrder order = foodOrderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        order.setStatus(status);

        foodOrderRepository.save(order);
    }
}