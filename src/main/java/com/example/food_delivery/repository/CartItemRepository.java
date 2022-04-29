package com.example.food_delivery.repository;

import com.example.food_delivery.model.CartItem;
import com.example.food_delivery.model.Customer;
import com.example.food_delivery.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository responsible for CartItem-related CRUD operations in the database.
 */
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    /**
     * Finds a CartItem in the cart of a given restaurant, referring to a given food item.
     * @param customer is the user in whose cart the food item is searched.
     * @param food is the food item which is searched.
     * @return the CartItem corresponding to the given user and food.
     */
    Optional<CartItem> findByCustomerAndFood(Customer customer, Food food);

    /**
     * Deletes all CartItems belonging to the given customer.
     * @param customer is the user whose cart items are deleted.
     */
    void deleteAllByCustomer(Customer customer);
}
