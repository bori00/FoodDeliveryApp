package com.example.food_delivery.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * Represents a menu item in the menu of a restaurant.
 */
@Entity
@Table(name = "food")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Food {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(length = 200)
    private String description;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "food_category_id", referencedColumnName = "Id")
    private FoodCategory foodCategory;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "Id")
    private Restaurant restaurant;

    /** The set of cart items in the cart of any user, referring to this menu item. */
    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL)
    private Set<CartItem> cartItems;

    public Food(String name, Double price, String description, FoodCategory foodCategory, Restaurant restaurant) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.foodCategory = foodCategory;
        this.restaurant = restaurant;
    }
}
