package com.example.food_delivery.model.DTO;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
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
 * DTO used for client-server communication, representing an order, exposing data visible to the
 * customer who placed the order.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class CustomerOrderDTO {
    private String restaurantName;

    private String status;

    private LocalDateTime dateTime;

    @JsonSerialize(keyUsing = FoodDTOSerializer.class)
    private Map<FoodDTO, Integer> orderedItemsToQuantity;

    /**
     * Custom serializer for sending the FoodDTO's in valid JSON format to the client instead of
     * sending just their hash-identifiers.
     */
    public static class FoodDTOSerializer extends JsonSerializer<FoodDTO> {

        private final ObjectMapper mapper = new ObjectMapper();

        @Override
        public void serialize(FoodDTO value,
                              JsonGenerator gen,
                              SerializerProvider serializers)
                throws IOException {

            StringWriter writer = new StringWriter();
            mapper.writeValue(writer, value);
            gen.writeFieldName(writer.toString());
        }
    }
}
