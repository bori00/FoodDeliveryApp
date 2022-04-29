package com.example.food_delivery.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Represents a geographical zone to which restaurants can deliver food.
 */
@Entity
@Table(name = "delivery_zone")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeliveryZone {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;
}
