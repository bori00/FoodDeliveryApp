package com.example.food_delivery.model.DTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class RestaurantDTO {
    @NotBlank(message = "The restaurant's name cannot be blank.")
    @Size(min = 3, max = 50, message = "The restaurant's name should have a length between 3 and " +
            "50")
    private String name;

    @NotBlank(message = "The restaurant's address cannot be blank.")
    @Size(min = 3, max = 500, message = "The restaurant's address should have a length between 3 " +
            "and 500")
    private String address;

    @NotEmpty(message = "Each restaurant should have at least one available delivery zone.")
    private Set<String> availableDeliveryZones;
}
