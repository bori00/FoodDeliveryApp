package com.example.food_delivery.repository;

import com.example.food_delivery.model.RestaurantAdmin;
import com.example.food_delivery.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository responsible for Admin-related CRUD operations in the database.
 */
public interface AdminRepository extends JpaRepository<User, Long> {
    /**
     * @param username the name of the searched admin.
     * @return the admin with the given username, if any, otherwise an empty Optional.
     */
    Optional<RestaurantAdmin> findByUserName(String username);
}
