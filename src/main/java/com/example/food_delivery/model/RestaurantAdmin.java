package com.example.food_delivery.model;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity(name = "Admin")
@ToString
@Setter
@NoArgsConstructor
public class RestaurantAdmin extends User {
    @OneToOne(mappedBy = "admin")
    private Restaurant restaurant;

    public RestaurantAdmin(String userName, String password) {
        super(userName, password);
    }
}
