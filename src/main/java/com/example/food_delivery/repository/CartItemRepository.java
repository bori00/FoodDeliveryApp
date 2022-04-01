package com.example.food_delivery.repository;

import com.example.food_delivery.model.CartItem;
import com.example.food_delivery.model.Customer;
import com.example.food_delivery.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCustomerAndFood(Customer customer, Food food);

    void deleteAlllByCustomer(Customer customer);
}
