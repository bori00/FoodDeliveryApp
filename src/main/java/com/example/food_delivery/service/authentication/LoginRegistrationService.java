package com.example.food_delivery.service.authentication;

import com.example.food_delivery.model.DTO.UserDTO;
import com.example.food_delivery.model.RestaurantAdmin;
import com.example.food_delivery.model.User;
import com.example.food_delivery.repository.UserRepository;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToAdminsException;
import com.example.food_delivery.service.authentication.exceptions.DuplicateUsernameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class LoginRegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationService authenticationService;

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

    public boolean hasRestaurant(String userName) {
        try {
            RestaurantAdmin admin = authenticationService.getCurrentAdmin();
            return admin.getRestaurant() != null;
        } catch (AccessRestrictedToAdminsException e) {
            return false;
        }
    }
}
