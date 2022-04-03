package com.example.food_delivery.service.food_browsing;

import com.example.food_delivery.model.DTO.FoodDTO;
import com.example.food_delivery.model.DTO.RestaurantDTO;
import com.example.food_delivery.model.DeliveryZone;
import com.example.food_delivery.model.Food;
import com.example.food_delivery.model.FoodCategory;
import com.example.food_delivery.model.Restaurant;
import com.example.food_delivery.repository.DeliveryZoneRepository;
import com.example.food_delivery.repository.RestaurantRepository;
import com.example.food_delivery.service.filtering.FilteringFacadeService;
import com.example.food_delivery.service.food_browsing.exceptions.RestaurantNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodBrowsingService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DeliveryZoneRepository deliveryZoneRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private FilteringFacadeService filteringFacadeService;

    public List<RestaurantDTO> getFilteredRestaurants(@Nullable String nameSubstring,
                                                      @Nullable String deliveryZoneName) {
        List<Restaurant> restaurants =
                filteringFacadeService.getFilteredRestaurants(nameSubstring, deliveryZoneName);

        return  restaurants
                .stream()
                .map(restaurant -> {
                    RestaurantDTO res = mapper.map(restaurant,
                            RestaurantDTO.class);
                    res.setAvailableDeliveryZoneNames(restaurant.getAvailableDeliveryZones().stream().map(DeliveryZone::getName).collect(Collectors.toSet()));
                    return res;
                })
                .collect(Collectors.toList());
    }

    public List<FoodDTO> getRestaurantMenu(String restaurantName,
                                           List<String> filterFoodCategoryNames) throws RestaurantNotFoundException {
        Optional<Restaurant> optRestaurant = restaurantRepository.findByName(restaurantName);

        if (optRestaurant.isEmpty()) {
            throw new RestaurantNotFoundException();
        }

        List<Food> foods = filteringFacadeService.getFilteredFoods(optRestaurant.get(),
                filterFoodCategoryNames);

        return foods
                .stream()
                .map(food -> mapper.map(food, FoodDTO.class))
                .collect(Collectors.toList());

    }

}
