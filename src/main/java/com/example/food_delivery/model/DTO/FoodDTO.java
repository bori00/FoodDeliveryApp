package com.example.food_delivery.model.DTO;

import com.example.food_delivery.model.FoodCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * DTO used for client-server communication, representing a menu item of a restaurant.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class FoodDTO {
    @NotBlank(message = "The name of a menu item cannot be blank.")
    @Size(min = 3, max = 50, message = "The name of a menu item must have a length between 3 and " +
            "50")
    private String name;

    @Positive(message = "The price must be >= 0")
    private Double price;

    @Size(max = 200, message = "The description of a menu item can be at most 200 characters long.")
    private String description;

    @NotBlank(message = "The food's category must be specified")
    private String foodCategory;
}
