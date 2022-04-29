package com.example.food_delivery.model;

import com.example.food_delivery.model.DTO.UserDTO;
import lombok.*;

import javax.persistence.*;

/**
 * Represents any user of the application.
 */
@Entity
@Table(name = "user")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false, unique = true, length = 30)
    private String userName;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 100)
    private String emailAddress;

    public User(String userName, String password, String emailAddress) {
        this.userName = userName;
        this.password = password;
        this.emailAddress = emailAddress;
    }
}
