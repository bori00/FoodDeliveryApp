package com.example.food_delivery.service.authentication;

import com.example.food_delivery.model.Customer;
import com.example.food_delivery.model.RestaurantAdmin;
import com.example.food_delivery.model.User;
import com.example.food_delivery.repository.AdminRepository;
import com.example.food_delivery.repository.CustomerRepository;
import com.example.food_delivery.repository.RestaurantRepository;
import com.example.food_delivery.repository.UserRepository;
import com.example.food_delivery.service.authentication.jwt.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * A Service which allows SPring Security to find a user by their username.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Finds a user by their username and returns the corresonding UserDetails for Spring Security.
     * @param username is the name of the user to be found.
     * @return the UserDetails of the user with the given name.
     * @throws UsernameNotFoundException if no user with the given username exists.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<RestaurantAdmin> optRestaurantAdmin = adminRepository.findByUserName(username);
        if (optRestaurantAdmin.isPresent()) {
            return new UserDetailsImpl(optRestaurantAdmin.get());
        }
        Optional<Customer> optCustomer = customerRepository.findByUserName(username);
        if (optCustomer.isPresent()) {
            return new UserDetailsImpl(optCustomer.get());
        }
        throw new UsernameNotFoundException("User Not Found with username: " + username);
    }

    /**
     * A class holding the details of a user in the format requested by SPring Security.
     */
    public static class UserDetailsImpl implements UserDetails {

        private final User user;
        private final Collection<GrantedAuthority> authorities;

        public enum Authorities {
            RESTAURANT_ADMIN,
            CUSTOMER
        }

        public UserDetailsImpl(RestaurantAdmin admin) {
            this.user = admin;
            this.authorities = List.of(new SimpleGrantedAuthority(Authorities.RESTAURANT_ADMIN.toString()));
        }

        public UserDetailsImpl(Customer customer) {
            this.user = customer;
            this.authorities = List.of(new SimpleGrantedAuthority(Authorities.CUSTOMER.toString()));
        }

        /** {@inheritDoc} */
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }

        /** {@inheritDoc} */
        @Override
        public String getPassword() {
            return user.getPassword();
        }

        /** {@inheritDoc} */
        @Override
        public String getUsername() {
            return user.getUserName();
        }

        /** {@inheritDoc} */
        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        /** {@inheritDoc} */
        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        /** {@inheritDoc} */
        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        /** {@inheritDoc} */
        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
