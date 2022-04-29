package com.example.food_delivery.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * Class representing a customer-type user.
 */
@Entity
@Table(name = "Customer")
@ToString
@Setter
@Getter
@NoArgsConstructor
public class Customer extends User {

    /** Set of orders places by the customer. */
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<FoodOrder> orders;

    /** Set of menu items placed in the cart by the customer. */
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<CartItem> cartItems;

    /**
     * @param userName is the name (identifier) of the customer.
     * @param password is the password of the user used for authentication.
     * @param emailAddress is the email address of the user.
     */
    public Customer(String userName, String password, String emailAddress) {
        super(userName, password, emailAddress);
    }
}
