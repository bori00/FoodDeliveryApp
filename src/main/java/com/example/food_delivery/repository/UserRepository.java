package com.example.food_delivery.repository;

import com.example.food_delivery.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
