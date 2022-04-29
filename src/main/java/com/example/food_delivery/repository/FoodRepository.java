package com.example.food_delivery.repository;

import com.example.food_delivery.model.Food;
import com.example.food_delivery.model.FoodCategory;
import com.example.food_delivery.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Repository responsible for FoodRepository-related CRUD operations in the database.
 */
public interface FoodRepository extends JpaRepository<Food, Long> {
    /**
     * Finds the food items with a given name, belonging to a given restaurant.
     * @param name is the name of the food item to be returned.
     * @param restaurant is the restaurant whose food items are searched.
     * @return the food item fulfilling the above criteria, if any, or an empty optional otherwise,
     */
    Optional<Food> findByNameAndRestaurant(String name, Restaurant restaurant);

    /**
     * Finds all food items belonging to a given restaurant and having one of the allowed food
     * categories.
     * @param restaurant is the restaurant whose food items are searched.
     * @param foodCategoryCollection is the collection of allowed food categories.
     * @return the food items fulfilling the above criteria.
     */
    List<Food> findByRestaurantAndFoodCategoryIsIn(Restaurant restaurant,
                                                   Collection<FoodCategory> foodCategoryCollection);

    /**
     * Returns the food items belonging to a given restaurant.
     * @param restaurant is the restaurant whose food items are searched.
     * @return the food items fulfilling the above criteria.
     */
    List<Food> findByRestaurant(Restaurant restaurant);
}
