package com.example.food_delivery.service.food_browsing;

import com.example.food_delivery.model.DTO.FoodDTO;
import com.example.food_delivery.model.DTO.RestaurantDTO;
import com.example.food_delivery.model.DeliveryZone;
import com.example.food_delivery.model.Food;
import com.example.food_delivery.model.Restaurant;
import com.example.food_delivery.repository.DeliveryZoneRepository;
import com.example.food_delivery.repository.RestaurantRepository;
import com.example.food_delivery.service.customer_order_management.CustomerOrdersService;
import com.example.food_delivery.service.filtering.FilteringFacadeService;
import com.example.food_delivery.service.food_browsing.exceptions.RestaurantNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service that allows users to search for menu items.
 */
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

    /**
     * Returns the list of restaurants which have the given nameSubstring as a substring of their
     * name and deliver food to the zone with name deliveryZoneName.
     * @param nameSubstring the substring searched in the name of the filtered restaurants. If null,
     *                     any restaurant name is accepted.
     * @param deliveryZoneName the zone to which the filtered restaurants must all deliver. If
     *                         null, then any restaurant is accepted.
     * @return the list of restaurants fulfilling both the nameSubstring and the deliveryZone
     * filtering criteria.
     */
    public List<RestaurantDTO> getFilteredRestaurants(@Nullable String nameSubstring,
                                                      @Nullable String deliveryZoneName) {
        List<Restaurant> restaurants =
                filteringFacadeService.getFilteredRestaurants(nameSubstring, deliveryZoneName);

        return  restaurants
                .stream()
                .map(restaurant -> {
                    RestaurantDTO res = mapper.map(restaurant,
                            RestaurantDTO.class);
                    res.setAvailableDeliveryZones(restaurant.getAvailableDeliveryZones().stream().map(DeliveryZone::getName).collect(Collectors.toSet()));
                    return res;
                })
                .collect(Collectors.toList());
    }

    /**
     * Returns the menu of the restaurant with the given name, but only the items within the
     * specified food categories.
     * @param restaurantName is the name of the restaurant whose menu is returned.
     * @param filterFoodCategoryNames are the names of the food categories whose menu items are
     *                                returned. Menu items from other categories are ignored.
     * @return a list of the menu items of the restaurant, from the requested food categories.
     * @throws RestaurantNotFoundException if no restaurant with the given name exists in the
     * database.
     */
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
