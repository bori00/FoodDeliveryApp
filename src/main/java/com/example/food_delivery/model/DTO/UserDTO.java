package com.example.food_delivery.model.DTO;

import com.example.food_delivery.model.Customer;
import com.example.food_delivery.model.RestaurantAdmin;
import com.example.food_delivery.model.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO used for client-server communication, representing a newly registered user.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
public class UserDTO {
    @NotBlank(message = "The username cannot be blank.")
    @Size(min = 3, max = 30, message = "The username should have a length between 3 and " +
            "30")
    private String name;

    @NotBlank(message = "The password cannot be blank.")
    @Size(min = 3, max = 100, message = "The password should have a length between 3 " +
            "and 100.")
    private String password;

    @NotBlank(message = "The email address cannot be blank.")
    @Size(min = 3, max = 100, message = "The email address should have a length between 3 " +
            "and 100.")
    private String emailAddress;

    @NotNull(message = "The user must have ADMIN or CUSTOMER type.")
    private UserType userType;

    /**
     * Enum for the different user types.
     */
    public enum UserType {
        ADMIN{
            @Override
            public User buildUser(UserDTO userDTO) {
                return new RestaurantAdmin(userDTO.getName(), userDTO.getPassword(), userDTO.getEmailAddress());
            }

        },
        CUSTOMER {
            @Override
            public User buildUser(UserDTO userDTO) {
                return new Customer(userDTO.getName(), userDTO.getPassword(), userDTO.getEmailAddress());
            }
        };

        /**
         * @param userDTO is the userDTO from which the new user is built.
         * @return the user built on based on the given userDTO, with the corresponding subtype
         * of the User class.
         */
        public abstract User buildUser(UserDTO userDTO);
    }
}
