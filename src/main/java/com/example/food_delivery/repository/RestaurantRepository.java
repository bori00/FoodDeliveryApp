package com.example.food_delivery.repository;

import com.example.food_delivery.model.DeliveryZone;
import com.example.food_delivery.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByName(String name);

    List<Restaurant> findAllByNameLike(String pattern);

    List<Restaurant> findAllByNameLikeAndAvailableDeliveryZonesContains(String pattern,
                                                                              DeliveryZone deliveryZone);

    List<Restaurant> findAllByAvailableDeliveryZonesContains(DeliveryZone deliveryZone);
}
