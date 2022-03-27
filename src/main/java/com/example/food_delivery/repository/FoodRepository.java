package com.example.food_delivery.repository;

import com.example.food_delivery.model.Food;
import com.example.food_delivery.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {
    Optional<Food> findByNameAndRestaurant(String name, Restaurant restaurant);
}
