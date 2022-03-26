package com.example.food_delivery.service.authentication;

import com.example.food_delivery.model.DTO.UserDTO;
import com.example.food_delivery.model.User;
import com.example.food_delivery.repository.UserRepository;
import com.example.food_delivery.service.authentication.exceptions.DuplicateUsernameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void register(UserDTO userDTO) throws DuplicateUsernameException {
        System.out.println(userDTO);

        // check that username is not taken
        Optional<User> userWithSameName = userRepository.findByUserName(userDTO.getName());
        if (userWithSameName.isPresent()) {
            throw new DuplicateUsernameException();
        }

        // save new user
        User user = UserFactory.buildUser(userDTO, passwordEncoder);
        userRepository.save(user);
    }
}
