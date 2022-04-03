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

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class CustomerCartDTO {
    private String restaurantName;

    @JsonSerialize(keyUsing = FoodDTOSerializer.class)
    private Map<FoodDTO, Integer> selectedItemsToQuantity;

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
