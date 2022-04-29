package com.example.food_delivery.repository;

import com.example.food_delivery.model.DeliveryZone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

/**
 * Repository responsible for DeliveryZone-related CRUD operations in the database.
 */
public interface DeliveryZoneRepository extends JpaRepository<DeliveryZone, Long> {
    /**
     * Finds all delivery zones whose names are present in the Iterable names.
     * @param names is the list of allowed names.
     * @return the delivery zones with their names in the list of the allowed names.
     */
    Set<DeliveryZone> findAllByNameIn(Iterable<String> names);

    /**
     * Returns the delivery zone with the requested name.
     * @param name if searched name.
     * @return the delivery zone with the given name.
     */
    Optional<DeliveryZone> findByName(String name);
}
