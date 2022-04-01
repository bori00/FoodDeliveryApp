package com.example.food_delivery.repository;

import com.example.food_delivery.model.RestaurantAdmin;
import com.example.food_delivery.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<User, Long> {
    Optional<RestaurantAdmin> findByUserName(String username);
}
