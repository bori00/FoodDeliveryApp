package com.example.food_delivery.repository;

import com.example.food_delivery.model.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {
    Optional<FoodCategory> findByName(String name);

    List<FoodCategory> findByNameIsIn(Collection<String> allowedName);
}