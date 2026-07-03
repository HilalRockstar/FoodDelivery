package com.example.FoodDeliveryApp.repository;

import com.example.FoodDeliveryApp.entity.MenuItem;
import com.example.FoodDeliveryApp.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository
        extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByRestaurant(Restaurant restaurant);

}