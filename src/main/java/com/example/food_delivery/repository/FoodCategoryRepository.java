package com.example.food_delivery.repository;

import com.example.food_delivery.model.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Repository responsible for FoodCategory-related CRUD operations in the database.
 */
public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {
    /**
     * @param name is the name of the searched FoodCategory.
     * @return the FoodCategory with the searched name, if any. Otheriwse, an empty optional is
     * returned.
     */
    Optional<FoodCategory> findByName(String name);

    /**
     * Finds the FoodCategories with their names present in the list of allowed names.
     * @param allowedNames is the collection of allowed names.
     * @return the list of foodCategories with their names belonging to allowedNames.
     */
    List<FoodCategory> findByNameIsIn(Collection<String> allowedNames);
}