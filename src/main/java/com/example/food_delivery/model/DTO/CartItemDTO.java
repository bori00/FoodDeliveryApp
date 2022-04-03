package com.example.food_delivery.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class CartItemDTO {
    @NotBlank(message = "The cart item must refer to an existing food")
    private String foodName;

    @PositiveOrZero(message = "The quantity must be >= 0")
    private Integer quantity;

    @NotBlank(message = "Each cart item must refer to a restaurant's menu item")
    private String restaurantName;
}
