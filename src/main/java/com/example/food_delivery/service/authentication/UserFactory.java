package com.example.food_delivery.service.authentication;

import com.example.food_delivery.model.DTO.UserDTO;
import com.example.food_delivery.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserFactory {
    public static User buildUser(UserDTO userDTO, PasswordEncoder passwordEncoder) {
       userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
       return userDTO.getUserType().buildUser(userDTO);
    }
}
