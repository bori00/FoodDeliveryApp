package com.example.food_delivery.service.authentication;

import com.example.food_delivery.model.Customer;
import com.example.food_delivery.model.RestaurantAdmin;
import com.example.food_delivery.model.User;
import com.example.food_delivery.repository.AdminRepository;
import com.example.food_delivery.repository.CustomerRepository;
import com.example.food_delivery.repository.RestaurantRepository;
import com.example.food_delivery.repository.UserRepository;
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

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CustomerRepository customerRepository;


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

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }

        @Override
        public String getPassword() {
            return user.getPassword();
        }

        @Override
        public String getUsername() {
            return user.getUserName();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
