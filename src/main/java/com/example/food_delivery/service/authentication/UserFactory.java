package com.example.food_delivery.service.authentication;

import com.example.food_delivery.model.DTO.UserDTO;
import com.example.food_delivery.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Factory class that builds a user from a given UserDTO.
 */
public class UserFactory {
    /**
     * Builds a User.
     * @param userDTO is the DTO based on which the new user is constructed.
     * @param passwordEncoder is the encoder to be applied on the user's password before
     *                        returning it.
     * @return the new user,
     */
    public static User buildUser(UserDTO userDTO, PasswordEncoder passwordEncoder) {
       userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
       return userDTO.getUserType().buildUser(userDTO);
    }
}
