package com.example.food_delivery.repository;

import com.example.food_delivery.model.DeliveryZone;
import com.example.food_delivery.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Repository responsible for Restaurant-related CRUD operations in the database.
 */
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    /**
     * @param name is the name of the searched restaurant.
     * @return the restaurant with the given name, if any, or any empty Optional otherwise.
     */
    Optional<Restaurant> findByName(String name);

    /**
     * Finds all restaurants whose names have as a substring the given name.
     * @param name must be the substring of the filtered restaurants' names.
     * @return the list of restaurants fulfilling the above criteria.
     */
    @Query("Select r from Restaurant r where r.name like %?1%")
    List<Restaurant> findAllByNameContaining(String name);

    /**
     * Finds all restaurants whose names have as a substring the given name and can deliver to
     * the requested delivery zone.
     * @param name must be the substring of the filtered restaurants' names.
     * @param deliveryZone is the zone to which all filtered restaurants must deliver food.
     * @return the list of restaurants fulfilling the above criteria.
     */
    @Query("Select r from Restaurant r, IN(r.availableDeliveryZones) dz where ?2 = dz and r.name " +
            "like %?1%")
    List<Restaurant> findAllByNameContainingAndAvailableDeliveryZonesContains(String name,
                                                                              DeliveryZone deliveryZone);

    /**
     * @param deliveryZone is the zone to which the filtered restaurants must deliver food.
     * @return the list of restaurants which can deliver food to the given zone.
     */
    @Query("Select r from Restaurant r, IN(r.availableDeliveryZones) dz where ?1 = dz")
    List<Restaurant> findAllByAvailableDeliveryZonesContains(DeliveryZone deliveryZone);
}
