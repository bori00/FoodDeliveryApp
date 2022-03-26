package com.example.food_delivery.model.DTO;

import com.example.food_delivery.model.FoodCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class FoodDTO {
    @NotBlank(message = "The name of a menu item cannot be blank.")
    @Size(min = 3, max = 50, message = "The name of a menu item must have a length between 3 and " +
            "50")
    private final String name;

    @Positive
    private final Double price;

    @Size(max = 200, message = "The description of a menu item can be at most 200 characters long.")
    private final String description;

    @NotBlank
    private final String foodCategory;
}
