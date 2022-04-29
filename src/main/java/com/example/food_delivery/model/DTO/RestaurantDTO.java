package com.example.food_delivery.model.DTO;

import lombok.*;

import javax.persistence.Access;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * DTO used for client-server communication, representing an Restaurant.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class RestaurantDTO {
    @NotBlank(message = "The restaurant's name cannot be blank.")
    @Size(min = 3, max = 50, message = "The restaurant's name should have a length between 3 and " +
            "50")
    private String name;

    @NotBlank(message = "The restaurant's address cannot be blank.")
    @Size(min = 3, max = 500, message = "The restaurant's address should have a length between 3 " +
            "and 500")
    private String address;

    private Set<String> availableDeliveryZones;
}
