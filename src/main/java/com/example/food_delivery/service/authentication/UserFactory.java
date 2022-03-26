package com.example.food_delivery.service.authentication;

import com.example.food_delivery.model.Customer;
import com.example.food_delivery.model.DTO.UserDTO;
import com.example.food_delivery.model.RestaurantAdmin;
import com.example.food_delivery.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserFactory {
    public static User buildUser(UserDTO userDTO, PasswordEncoder passwordEncoder) {
        switch (userDTO.getUserType()) {
            case ADMIN:
                return new RestaurantAdmin(userDTO.getName(),
                        passwordEncoder.encode(userDTO.getPassword()));
            case CUSTOMER:
                return new Customer(userDTO.getName(),
                        passwordEncoder.encode(userDTO.getPassword()));
        }
        throw new IllegalArgumentException("Unmknown user type");
    }
}
