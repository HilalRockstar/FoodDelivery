package com.example.FoodDeliveryApp.controller;

import com.example.FoodDeliveryApp.entity.CartItem;
import com.example.FoodDeliveryApp.entity.FoodOrder;
import com.example.FoodDeliveryApp.entity.OrderItem;
import com.example.FoodDeliveryApp.enums.OrderStatus;
import com.example.FoodDeliveryApp.service.AdminOrderService;
import com.example.FoodDeliveryApp.service.CartService;
import com.example.FoodDeliveryApp.service.DeliveryDashboardService;
import com.example.FoodDeliveryApp.service.DeliveryPartnerService;
import com.example.FoodDeliveryApp.service.FoodOrderService;
import com.example.FoodDeliveryApp.service.MenuItemService;
import com.example.FoodDeliveryApp.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequiredArgsConstructor
public class ApiController {

    private final RestaurantService restaurantService;
    private final MenuItemService menuItemService;
    private final CartService cartService;
    private final FoodOrderService foodOrderService;
    private final AdminOrderService adminOrderService;
    private final DeliveryPartnerService deliveryPartnerService;
    private final DeliveryDashboardService deliveryDashboardService;

    @GetMapping("/restaurants")
    public List<?> restaurants() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/restaurants/{restaurantId}/menu")
    public List<?> menu(@PathVariable Long restaurantId) {
        return menuItemService.getMenuItemsByRestaurant(restaurantId);
    }

    @GetMapping("/cart")
    public List<Map<String, Object>> cart(Authentication authentication) {
        return cartService.getMyCart(authentication.getName()).stream()
                .map(this::cartItem)
                .toList();
    }

    @GetMapping("/orders")
    public List<Map<String, Object>> orders(Authentication authentication) {
        return foodOrderService.getMyOrders(authentication.getName()).stream()
                .map(this::order)
                .toList();
    }

    @GetMapping("/orders/{id}")
    public Map<String, Object> orderDetails(@PathVariable Long id) {
        return order(foodOrderService.getOrderById(id));
    }

    @GetMapping("/admin/restaurants")
    public List<?> adminRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/admin/restaurants/{id}/menu")
    public List<?> adminMenu(@PathVariable Long id) {
        return menuItemService.getMenuItemsByRestaurant(id);
    }

    @GetMapping("/admin/delivery-partners")
    public List<?> deliveryPartners() {
        return deliveryPartnerService.getAllDeliveryPartners();
    }

    @GetMapping("/admin/orders")
    public List<Map<String, Object>> adminOrders() {
        return adminOrderService.getAllOrders().stream().map(this::order).toList();
    }

    @GetMapping("/delivery/orders")
    public List<Map<String, Object>> assignedOrders(Authentication authentication) {
        return deliveryDashboardService.getAssignedOrders(authentication.getName())
                .stream().map(this::order).toList();
    }

    @GetMapping("/delivery/orders/{id}")
    public Map<String, Object> assignedOrder(@PathVariable Long id) {
        return order(deliveryDashboardService.getOrderById(id));
    }

    @PostMapping("/cart/add/{menuItemId}")
    public ResponseEntity<Void> addToCart(
            @PathVariable Long menuItemId,
            Authentication authentication) {

        boolean added = cartService.addToCart(menuItemId, authentication.getName());

        if (!added) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/cart/increase/{cartItemId}")
    public ResponseEntity<Void> increaseCartItem(@PathVariable Long cartItemId) {
        cartService.increaseQuantity(cartItemId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cart/decrease/{cartItemId}")
    public ResponseEntity<Void> decreaseCartItem(@PathVariable Long cartItemId) {
        cartService.decreaseQuantity(cartItemId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cart/remove/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long cartItemId) {
        cartService.removeFromCart(cartItemId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cart/clear")
    public ResponseEntity<Void> clearCart(Authentication authentication) {
        cartService.clearCart(authentication.getName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/orders/place")
    public ResponseEntity<Void> placeOrder(Authentication authentication) {
        foodOrderService.placeOrder(authentication.getName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin/orders/{orderId}/assign-delivery")
    public ResponseEntity<Void> assignDeliveryPartner(
            @PathVariable Long orderId,
            @RequestBody Map<String, Long> payload) {

        Long deliveryPartnerId = payload.get("deliveryPartnerId");
        adminOrderService.assignDeliveryPartner(orderId, deliveryPartnerId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin/orders/{orderId}/status")
    public ResponseEntity<Void> updateAdminOrderStatus(
            @PathVariable Long orderId,
            @RequestBody Map<String, String> payload) {

        adminOrderService.updateOrderStatus(orderId, OrderStatus.valueOf(payload.get("status")));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delivery/orders/{orderId}/status")
    public ResponseEntity<Void> updateDeliveryOrderStatus(
            @PathVariable Long orderId,
            @RequestBody Map<String, String> payload) {

        deliveryDashboardService.updateOrderStatus(orderId, OrderStatus.valueOf(payload.get("status")));
        return ResponseEntity.ok().build();
    }

    private Map<String, Object> cartItem(CartItem item) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("cartItemId", item.getId());
        result.put("menuItemId", item.getMenuItem().getId());
        result.put("menuItemName", item.getMenuItem().getName());
        result.put("price", item.getMenuItem().getPrice());
        result.put("quantity", item.getQuantity());
        result.put("totalPrice", item.getMenuItem().getPrice() * item.getQuantity());
        return result;
    }

    private Map<String, Object> order(FoodOrder foodOrder) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("id", foodOrder.getId());
        result.put("orderDate", foodOrder.getOrderDate());
        result.put("totalAmount", foodOrder.getTotalAmount());
        result.put("status", foodOrder.getStatus());
        if (foodOrder.getRestaurant() != null) {
            result.put("restaurant", Map.of("name", foodOrder.getRestaurant().getName()));
        }
        if (foodOrder.getUser() != null) {
            result.put("user", Map.of("fullName", foodOrder.getUser().getFullName()));
        }
        if (foodOrder.getDeliveryPartner() != null) {
            result.put("deliveryPartner", Map.of(
                    "id", foodOrder.getDeliveryPartner().getId(),
                    "fullName", foodOrder.getDeliveryPartner().getFullName(),
                    "available", foodOrder.getDeliveryPartner().isAvailable()));
        }
        List<OrderItem> items = foodOrder.getOrderItems();
        if (items != null) {
            result.put("items", items.stream().map(item -> Map.of(
                    "name", item.getMenuItem().getName(),
                    "quantity", item.getQuantity(),
                    "price", item.getPrice())).toList());
        }
        return result;
    }
}