package com.example.food_delivery.repository;

import com.example.food_delivery.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * Repository responsible for Customer-related CRUD operations in the database.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUserName(String userName);
}
