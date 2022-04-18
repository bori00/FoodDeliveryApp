package com.example.food_delivery.service.authentication;

import com.example.food_delivery.model.Customer;
import com.example.food_delivery.model.RestaurantAdmin;
import com.example.food_delivery.model.User;
import com.example.food_delivery.repository.AdminRepository;
import com.example.food_delivery.repository.CustomerRepository;
import com.example.food_delivery.repository.UserRepository;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToAdminsException;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToCustomersException;
import com.example.food_delivery.service.authentication.exceptions.AuthenticationRequiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service that implements functionalities related to user authentication: it provides data about
 * the currently logged in user.
 */
@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Returns the currently logged in user.
     * @return the currently logged in user.
     * @throws AuthenticationRequiredException if no authenticated user exists.
     */
    public User getCurrentUser() throws AuthenticationRequiredException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optUser =
                userRepository.findByUserName(((UserDetailsServiceImpl.UserDetailsImpl) auth.getPrincipal()).getUsername());
        if (optUser.isEmpty()) {
            throw new AuthenticationRequiredException();
        }
        return optUser.get();
    }

    /**
     * Returns the currently logged in admin.
     * @return the currently logged in admin.
     * @throws AccessRestrictedToAdminsException, if no authenticated user exists, or the
     * logged-in user is not an admin.
     */
    public RestaurantAdmin getCurrentAdmin() throws AccessRestrictedToAdminsException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<RestaurantAdmin> optUser =
                adminRepository.findByUserName(((UserDetailsServiceImpl.UserDetailsImpl) auth.getPrincipal()).getUsername());
        if (optUser.isEmpty()) {
            throw new AccessRestrictedToAdminsException();
        }
        return optUser.get();
    }

    /**
     * Returns the currently logged in customer.
     * @return the currently logged in customer.
     * @throws AccessRestrictedToCustomersException, if no authenticated user exists, or the
     * logged-in user is not a customer.
     */
    public Customer getCurrentCustomer() throws AccessRestrictedToCustomersException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Customer> optUser =
                customerRepository.findByUserName(((UserDetailsServiceImpl.UserDetailsImpl) auth.getPrincipal()).getUsername());
        if (optUser.isEmpty()) {
            throw new AccessRestrictedToCustomersException();
        }
        return optUser.get();
    }
}
