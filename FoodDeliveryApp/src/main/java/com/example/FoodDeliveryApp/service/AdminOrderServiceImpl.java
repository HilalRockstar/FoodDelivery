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
public class AdminOrderServiceImpl
        implements AdminOrderService {

    private final FoodOrderRepository foodOrderRepository;
    private final DeliveryPartnerRepository deliveryPartnerRepository;

    @Override
    public List<FoodOrder> getAllOrders() {

        return foodOrderRepository.findAllByOrderByIdDesc();
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

        FoodOrder foodOrder = foodOrderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        foodOrder.setStatus(status);

        foodOrderRepository.save(foodOrder);
    }
    @Override
    public void assignDeliveryPartner(
            Long orderId,
            Long deliveryPartnerId) {

        // Find Order
        FoodOrder order = foodOrderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        // Find Delivery Partner
        DeliveryPartner deliveryPartner =
                deliveryPartnerRepository.findById(deliveryPartnerId)
                        .orElseThrow(() ->
                                new RuntimeException("Delivery Partner not found"));

        // Assign Delivery Partner
        order.setDeliveryPartner(deliveryPartner);

        // Save Order
        foodOrderRepository.save(order);
    }
}