package com.example.food_delivery.repository;

import com.example.food_delivery.model.Food;
import com.example.food_delivery.model.FoodCategory;
import com.example.food_delivery.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {
    Optional<Food> findByNameAndRestaurant(String name, Restaurant restaurant);

    List<Food> findByRestaurantAndFoodCategoryIsIn(Restaurant restaurant,
                                                   Collection<FoodCategory> foodCategoryCollection);

    List<Food> findByRestaurant(Restaurant restaurant);
}
