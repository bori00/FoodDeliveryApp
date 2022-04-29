package com.example.food_delivery.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Represents an item of an order, referring to a given menu item with a given quantity,
 */
@Entity
@Table(name = "order_item")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItem {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "order_id", referencedColumnName = "Id")
    private FoodOrder foodOrder;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "food_id", referencedColumnName = "Id")
    private Food food;

    public OrderItem(Integer quantity, FoodOrder foodOrder, Food food) {
        this.quantity = quantity;
        this.foodOrder = foodOrder;
        this.food = food;
    }
}
