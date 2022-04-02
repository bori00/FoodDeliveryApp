package com.example.food_delivery.repository;

import com.example.food_delivery.model.DeliveryZone;
import com.example.food_delivery.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByName(String name);

    @Query("Select r from Restaurant r where r.name like %?1%")
    List<Restaurant> findAllByNameContaining(String name);

    @Query("Select r from Restaurant r, IN(r.availableDeliveryZones) dz where ?2 = dz and r.name " +
            "like %?1%")
    List<Restaurant> findAllByNameContainingAndAvailableDeliveryZonesContains(String name,
                                                                              DeliveryZone deliveryZone);

    @Query("Select r from Restaurant r, IN(r.availableDeliveryZones) dz where ?1 = dz")
    List<Restaurant> findAllByAvailableDeliveryZonesContains(DeliveryZone deliveryZone);
}
