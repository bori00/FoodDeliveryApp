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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdminOrderDTO that = (AdminOrderDTO) o;

        if (Id != null ? !Id.equals(that.Id) : that.Id != null) return false;
        if (clientName != null ? !clientName.equals(that.clientName) : that.clientName != null)
            return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (dateTime != null ? !dateTime.equals(that.dateTime) : that.dateTime != null)
            return false;
        return orderedItemsToQuantity != null ? orderedItemsToQuantity.equals(that.orderedItemsToQuantity) : that.orderedItemsToQuantity == null;
    }

    @Override
    public int hashCode() {
        int result = Id != null ? Id.hashCode() : 0;
        result = 31 * result + (clientName != null ? clientName.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        result = 31 * result + (orderedItemsToQuantity != null ? orderedItemsToQuantity.hashCode() : 0);
        return result;
    }
}
