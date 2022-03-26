package com.example.food_delivery.service.authentication;

import com.example.food_delivery.model.Customer;
import com.example.food_delivery.model.DTO.UserDTO;
import com.example.food_delivery.model.RestaurantAdmin;
import com.example.food_delivery.model.User;

public class UserFactory {
    public static User buildUser(UserDTO userDTO) {
        switch (userDTO.getUserType()) {
            case ADMIN:
                return new RestaurantAdmin(userDTO.getName(), userDTO.getPassword());
            case CUSTOMER:
                return new Customer(userDTO.getName(), userDTO.getPassword());
        }
        throw new IllegalArgumentException("Unmknown user type");
    }
}
