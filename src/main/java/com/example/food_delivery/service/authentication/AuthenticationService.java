package com.example.food_delivery.service.authentication;

import com.example.food_delivery.model.Customer;
import com.example.food_delivery.model.RestaurantAdmin;
import com.example.food_delivery.model.User;
import com.example.food_delivery.repository.AdminRepository;
import com.example.food_delivery.repository.CustomerRepository;
import com.example.food_delivery.repository.UserRepository;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToAdminsException;
import com.example.food_delivery.service.authentication.exceptions.AuthenticationRequiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public User getCurrentUser() throws AuthenticationRequiredException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optUser =
                userRepository.findByUserName(((CustomUserDetailsService.CustomUserDetails) auth.getPrincipal()).getUsername());
        if (optUser.isEmpty()) {
            throw new AuthenticationRequiredException();
        }
        return optUser.get();
    }

    public RestaurantAdmin getCurrentAdmin() throws AccessRestrictedToAdminsException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<RestaurantAdmin> optUser =
                adminRepository.findByUserName(((CustomUserDetailsService.CustomUserDetails) auth.getPrincipal()).getUsername());
        if (optUser.isEmpty()) {
            throw new AccessRestrictedToAdminsException();
        }
        return optUser.get();
    }

    public Customer getCurrentCustomer() throws AccessRestrictedToAdminsException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Customer> optUser =
                customerRepository.findByUserName(((CustomUserDetailsService.CustomUserDetails) auth.getPrincipal()).getUsername());
        if (optUser.isEmpty()) {
            throw new AccessRestrictedToAdminsException();
        }
        return optUser.get();
    }
}
