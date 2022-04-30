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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Food food = (Food) o;

        if (Id != null ? !Id.equals(food.Id) : food.Id != null) return false;
        if (name != null ? !name.equals(food.name) : food.name != null) return false;
        if (price != null ? !price.equals(food.price) : food.price != null) return false;
        if (description != null ? !description.equals(food.description) : food.description != null)
            return false;
        if (foodCategory != null ? !foodCategory.equals(food.foodCategory) : food.foodCategory != null)
            return false;
        if (restaurant != null ? !restaurant.equals(food.restaurant) : food.restaurant != null)
            return false;
        return cartItems != null ? cartItems.equals(food.cartItems) : food.cartItems == null;
    }

    @Override
    public int hashCode() {
        int result = Id != null ? Id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (foodCategory != null ? foodCategory.hashCode() : 0);
        result = 31 * result + (restaurant != null ? restaurant.hashCode() : 0);
        result = 31 * result + (cartItems != null ? cartItems.hashCode() : 0);
        return result;
    }
}
