package com.example.food_delivery.service.authentication;

import com.example.food_delivery.model.DTO.UserDTO;
import com.example.food_delivery.model.RestaurantAdmin;
import com.example.food_delivery.model.User;
import com.example.food_delivery.repository.UserRepository;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToAdminsException;
import com.example.food_delivery.service.authentication.exceptions.DuplicateUsernameException;
import com.example.food_delivery.service.authentication.jwt.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service that implements functionalities related to user authentication: registration.
 */
@Service
public class LoginRegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationService authenticationService;

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    /**
     * Registers a user with the data specified in the userDTO.
     * @param userDTO holds the registration data.
     * @throws DuplicateUsernameException if the requested username is already taken.
     */
    public void register(UserDTO userDTO) throws DuplicateUsernameException {
        System.out.println(userDTO);

        // check that username is not taken
        Optional<User> userWithSameName = userRepository.findByUserName(userDTO.getName());
        if (userWithSameName.isPresent()) {
            logger.info(String.format("INVALID UPDATE - Failed registration: %s name already " +
                    "taken.", userDTO.getName()));
            throw new DuplicateUsernameException();
        }

        // save new user
        User user = UserFactory.buildUser(userDTO, passwordEncoder);

        logger.info(String.format("UPDATE - new user registered with name %s.",
                user.getUserName()));
        userRepository.save(user);
    }

    /**
     * Tells whether the currently logged-in admin has a restaurant.
     * @return true if the currently logged-in user has a restaurant, false otherwise.
     */
    public boolean hasRestaurant() {
        try {
            RestaurantAdmin admin = authenticationService.getCurrentAdmin();
            return admin.getRestaurant() != null;
        } catch (AccessRestrictedToAdminsException e) {
            return false;
        }
    }
}
