package com.example.food_delivery.repository;

import com.example.food_delivery.model.DeliveryZone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface DeliveryZoneRepository extends JpaRepository<DeliveryZone, Long> {
    Set<DeliveryZone> findAllByNameIn(Iterable<String> name);

    Optional<DeliveryZone> findByName(String name);
}
