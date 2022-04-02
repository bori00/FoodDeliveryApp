package com.example.food_delivery.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class DeliveryZoneDTO {
    @Size(min = 3, max=50, message = "A delivery zone must have a length between 3 and 50")
    private String name;
}
