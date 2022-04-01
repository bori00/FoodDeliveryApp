package com.example.food_delivery.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
