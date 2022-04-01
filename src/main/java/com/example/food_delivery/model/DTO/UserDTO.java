package com.example.food_delivery.model.DTO;

import com.example.food_delivery.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@AllArgsConstructor
@ToString
public class UserDTO {
    @NotBlank(message = "The username cannot be blank.")
    @Size(min = 3, max = 30, message = "The username should have a length between 3 and " +
            "30")
    private final String name;

    @NotBlank(message = "The password cannot be blank.")
    @Size(min = 3, max = 100, message = "The password should have a length between 3 " +
            "and 100")
    private final String password;

    @NotNull(message = "The user must have ADMIN or CUSTOMER type")
    private final User.UserType userType;
}
