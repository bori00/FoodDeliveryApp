package com.example.food_delivery.model;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "Customer")
@ToString
@Setter
@NoArgsConstructor
public class Customer extends User {

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<FoodOrder> orders;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<CartItem> cartItems;

    public Customer(String userName, String password) {
        super(userName, password);
    }
}
