package com.example.food_delivery.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "restaurant")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Restaurant {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(nullable = false, length = 500)
    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    private RestaurantAdmin admin;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "available_delivery_zones",
            joinColumns = {@JoinColumn(name = "restaurant_id")},
            inverseJoinColumns = {@JoinColumn(name = "delivery_zone_id")}
    )
    private Set<DeliveryZone> availableDeliveryZones;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private Set<Food> foods;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private Set<FoodOrder> orders;

    public Restaurant(String name, String address, RestaurantAdmin admin, Set<DeliveryZone> availableDeliveryZones) {
        this.name = name;
        this.address = address;
        this.admin = admin;
        this.availableDeliveryZones = availableDeliveryZones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Restaurant that = (Restaurant) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }
}
