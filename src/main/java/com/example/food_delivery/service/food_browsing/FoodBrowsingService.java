package com.example.food_delivery.service.food_browsing;

import com.example.food_delivery.model.DTO.FoodDTO;
import com.example.food_delivery.model.DTO.RestaurantDTO;
import com.example.food_delivery.model.DeliveryZone;
import com.example.food_delivery.model.Restaurant;
import com.example.food_delivery.repository.DeliveryZoneRepository;
import com.example.food_delivery.repository.RestaurantRepository;
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

    public List<RestaurantDTO> getFilteredRestaurants(@Nullable String nameSubstring,
                                                      @Nullable String deliveryZoneName) {
        List<Restaurant> restaurants = new ArrayList<>();

        Optional<DeliveryZone> deliveryZone = Optional.empty();
        if (deliveryZoneName != null) {
            deliveryZone = deliveryZoneRepository.findByName(deliveryZoneName);
        }

        if (nameSubstring != null && deliveryZone.isPresent()) {
            restaurants =
                    restaurantRepository.findAllByNameContainingAndAvailableDeliveryZonesContains(
                            nameSubstring, deliveryZone.get());
        } else if (nameSubstring != null) {
            restaurants = restaurantRepository.findAllByNameContaining(nameSubstring);
        } else if (deliveryZone.isPresent()) {
            restaurants =
                    restaurantRepository.findAllByAvailableDeliveryZonesContains(deliveryZone.get());
        } else {
            restaurants = restaurantRepository.findAll();
        }

        return  restaurants.stream().map(restaurant -> mapper.map(restaurant,
                RestaurantDTO.class)).collect(Collectors.toList());
    }

    public List<FoodDTO> getRestaurantMenu(String restaurantName) throws RestaurantNotFoundException {
        Optional<Restaurant> optRestaurant = restaurantRepository.findByName(restaurantName);

        if (optRestaurant.isEmpty()) {
            throw new RestaurantNotFoundException();
        }

        return optRestaurant.get()
                .getFoods()
                .stream()
                .map(food -> mapper.map(food, FoodDTO.class))
                .collect(Collectors.toList());

    }

}
