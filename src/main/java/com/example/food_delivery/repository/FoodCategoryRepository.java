package com.example.food_delivery.repository;

import com.example.food_delivery.model.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {
}