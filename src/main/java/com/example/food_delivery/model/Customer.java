package com.example.food_delivery.model;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;

@Entity(name = "Customer")
@ToString
@Setter
@NoArgsConstructor
public class Customer extends User {
    public Customer(String userName, String password) {
        super(userName, password);
    }
}
