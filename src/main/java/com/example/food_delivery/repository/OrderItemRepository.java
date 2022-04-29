package com.example.food_delivery.repository;

import com.example.food_delivery.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository responsible for OrderItem-related CRUD operations in the database.
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
