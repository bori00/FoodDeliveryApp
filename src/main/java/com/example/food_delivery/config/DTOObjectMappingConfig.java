package com.example.food_delivery.config;

import com.example.food_delivery.model.DTO.FoodDTO;
import com.example.food_delivery.model.Food;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DTOObjectMappingConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper =  new ModelMapper();

        // Food -> FoodDTO mapping
        TypeMap<Food, FoodDTO> propertyMapper1 = mapper.createTypeMap(Food.class,
                FoodDTO.class);
        propertyMapper1.addMapping(
                srcFood -> srcFood.getFoodCategory().getName(),
                FoodDTO::setFoodCategory
        );


        return mapper;
    }
}
