package com.example.food_delivery.repository;

import com.example.food_delivery.model.FoodOrder;
import com.example.food_delivery.model.Restaurant;
import com.example.food_delivery.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface FoodOrderRepository extends JpaRepository<FoodOrder, Long> {

    List<FoodOrder> findAllByOrderStatusInAndRestaurantOrderByDateTimeDesc(Collection<FoodOrder.OrderStatus> statuses, Restaurant restaurant);
}
