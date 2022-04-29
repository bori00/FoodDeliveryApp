package com.example.food_delivery.config;

import com.example.food_delivery.model.DTO.FoodDTO;
import com.example.food_delivery.model.DTO.RestaurantDTO;
import com.example.food_delivery.model.DeliveryZone;
import com.example.food_delivery.model.Food;
import com.example.food_delivery.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * COnfiguration class that configures the model <-> DTO mapper.
 */
@Configuration
public class DTOObjectMappingConfig {

    /**
     * Builds a Mapper that maps any DTO to the corresponding model object and vice-versa.
     * @return the mapper.
     */
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