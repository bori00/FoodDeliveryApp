package com.example.food_delivery.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Class representing an item in a user's cart.
 */
@Entity
@Table(name = "cart_item")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItem {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "customer_id", referencedColumnName = "Id")
    private Customer customer;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "food_id", referencedColumnName = "Id")
    private Food food;

    /**
     * Builds a CartItem.
     * @param quantity is the quantity of the food item that the user placed in the cart.
     * @param customer is the user who placed the item in their cart.
     * @param food is the menu item placed into the cart.
     */
    public CartItem(Integer quantity, Customer customer, Food food) {
        this.quantity = quantity;
        this.customer = customer;
        this.food = food;
    }

    @PreRemove
    private void preRemove() {
        customer.getCartItems().remove(this);
        food.getCartItems().remove(this);
    }
}
