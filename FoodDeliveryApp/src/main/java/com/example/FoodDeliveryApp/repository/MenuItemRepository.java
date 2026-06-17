package com.example.FoodDeliveryApp.repository;

import com.example.FoodDeliveryApp.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemRepository
        extends JpaRepository<MenuItem, Long> {
}