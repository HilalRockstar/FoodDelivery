package com.example.FoodDeliveryApp.service;

import com.example.FoodDeliveryApp.entity.FoodOrder;
import com.example.FoodDeliveryApp.enums.OrderStatus;
import com.example.FoodDeliveryApp.repository.FoodOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminOrderServiceImpl
        implements AdminOrderService {

    private final FoodOrderRepository foodOrderRepository;

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
}