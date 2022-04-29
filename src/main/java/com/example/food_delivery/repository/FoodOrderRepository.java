package com.example.food_delivery.repository;

import com.example.food_delivery.model.FoodOrder;
import com.example.food_delivery.model.Restaurant;
import com.example.food_delivery.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Repository responsible for FoodOrder-related CRUD operations in the database.
 */
public interface FoodOrderRepository extends JpaRepository<FoodOrder, Long> {

    /**
     * Finds all food orders by status and restaurant, and returns them sorted in a decreasing
     * order by their timestamp.
     * @param statuses if the collection of order statuses allowed. Orders with another status
     *                 will be ignored.
     * @param restaurant is the restaurant whose orders are returned.
     * @return the orders belonging to the given restaurant with any of the given statuses,
     * sorted in a decreasing order by their timestamp.
     */
    List<FoodOrder> findAllByOrderStatusInAndRestaurantOrderByDateTimeDesc(Collection<FoodOrder.OrderStatus> statuses, Restaurant restaurant);
}
