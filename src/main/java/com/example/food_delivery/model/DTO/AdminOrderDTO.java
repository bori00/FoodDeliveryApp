package com.example.food_delivery.model.DTO;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * DTO used for client-server communication, representing an order, exposing data visible to admins.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class AdminOrderDTO {
    private Long Id;

    private String clientName;

    private String status;

    private LocalDateTime dateTime;

    @JsonSerialize(keyUsing = CustomerOrderDTO.FoodDTOSerializer.class)
    private Map<FoodDTO, Integer> orderedItemsToQuantity;
}
