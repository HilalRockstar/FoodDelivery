package com.example.FoodDeliveryApp.service;

import com.example.FoodDeliveryApp.entity.CartItem;
import com.example.FoodDeliveryApp.entity.FoodOrder;
import com.example.FoodDeliveryApp.entity.OrderItem;
import com.example.FoodDeliveryApp.entity.User;
import com.example.FoodDeliveryApp.enums.OrderStatus;
import com.example.FoodDeliveryApp.repository.CartItemRepository;
import com.example.FoodDeliveryApp.repository.FoodOrderRepository;
import com.example.FoodDeliveryApp.repository.OrderItemRepository;
import com.example.FoodDeliveryApp.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodOrderServiceImpl implements FoodOrderService {

    private final UserRepository userRepository;

    private final CartItemRepository cartRepository;

    private final FoodOrderRepository foodOrderRepository;

    private final OrderItemRepository orderItemRepository;

    @Override
    public void placeOrder(String email) {
        //Find User
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
        //Get a Cart Items
        List<CartItem> cartItems = cartRepository.findByUser(user);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        //Find Total Amount
        double totalAmount = 0.0;

        for (CartItem cartItem : cartItems) {

            totalAmount += cartItem.getMenuItem().getPrice()
                    * cartItem.getQuantity();
        }

        //Create Food Order like bill and status innum save agala athan next we did
        FoodOrder foodOrder = FoodOrder.builder()
                .user(user)
                .totalAmount(totalAmount)
                .status(OrderStatus.PENDING)
                .build();

        foodOrderRepository.save(foodOrder);
        //Why create new OrderItem?
        //
        //Because CartItem is temporary.
        //
        //After order success,
        //
        //Cart empty panniduvom.
        //
        //So CartItem table-la data irukkaadhu.
        //
        //But Order History irukkanum.
        //
        //Adhanala
        //
        //CartItem ➜ OrderItem
        //
        //copy pannrom.

    // Convert CartItem ➜ OrderItem
    //OrderItem la Save pannurom
        for (CartItem cartItem : cartItems) {

            OrderItem orderItem = OrderItem.builder()
                    .foodOrder(foodOrder)
                    .menuItem(cartItem.getMenuItem())
                    .quantity(cartItem.getQuantity())
                    .price(cartItem.getMenuItem().getPrice())
                    .build();

            orderItemRepository.save(orderItem);
        }
        //For Delete Cart
        cartRepository.deleteByUser(user);
    }
    //Get Orders
    @Override
    public List<FoodOrder> getMyOrders(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return foodOrderRepository.findByUserOrderByIdDesc(user);
    }
    @Override
    public FoodOrder getOrderById(Long id) {

        return foodOrderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));
    }
    @Override
    public List<OrderItem> getOrderItems(Long orderId) {

        FoodOrder order = foodOrderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        return orderItemRepository.findByFoodOrder(order);
    }
}