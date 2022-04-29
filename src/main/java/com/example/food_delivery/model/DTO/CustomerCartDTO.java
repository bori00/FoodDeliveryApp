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
import java.util.Map;

/**
 * DTO used for client-server communication, representing the cart of a customer.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class CustomerCartDTO {
    private String restaurantName;

    @JsonSerialize(keyUsing = FoodDTOSerializer.class)
    private Map<FoodDTO, Integer> selectedItemsToQuantity;

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
