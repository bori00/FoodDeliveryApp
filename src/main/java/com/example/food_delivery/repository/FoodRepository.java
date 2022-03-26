package com.example.food_delivery.repository;

import com.example.food_delivery.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
}
