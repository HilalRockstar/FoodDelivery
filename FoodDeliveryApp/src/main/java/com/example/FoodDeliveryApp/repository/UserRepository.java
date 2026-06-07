package com.example.FoodDeliveryApp.repository;
import com.example.FoodDeliveryApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //why this methods because of when register the email it already present or not
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

}