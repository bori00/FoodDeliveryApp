package com.example.food_delivery.service.filtering;

import com.example.food_delivery.model.DeliveryZone;
import com.example.food_delivery.model.Food;
import com.example.food_delivery.model.FoodCategory;
import com.example.food_delivery.model.Restaurant;
import com.example.food_delivery.repository.DeliveryZoneRepository;
import com.example.food_delivery.repository.FoodCategoryRepository;
import com.example.food_delivery.repository.FoodRepository;
import com.example.food_delivery.repository.RestaurantRepository;
import net.bytebuddy.implementation.bind.annotation.Empty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Facade that simplifies interaction with the repositories in case multiple filtering criteria
 * is to be applied, but some of these may be missing.
 *
 * The facade's methods treat null-values filtering criteria as missing and call the
 * corresponding methods of the repositories.
 */
@Service
public class FilteringFacadeService {

    @Autowired
    private FoodCategoryRepository foodCategoryRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private DeliveryZoneRepository deliveryZoneRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    /**
     * @param restaurant: mandatory
     * @param allowedFoodCategoryNames: may be empty or null. In both cases, all food categories
     *                                are considered to be allowed.
     * @return the list of food from the given restaurant, in any of the allowed food categories.
     */
    public List<Food> getFilteredFoods(Restaurant restaurant,
                                       @Nullable @Empty Collection<String> allowedFoodCategoryNames) {

        if (allowedFoodCategoryNames == null || allowedFoodCategoryNames.isEmpty()) {
            return foodRepository.findByRestaurant(restaurant);
        } else {
            List<FoodCategory> foodCategories =
                    foodCategoryRepository.findByNameIsIn(allowedFoodCategoryNames);

            return foodRepository.findByRestaurantAndFoodCategoryIsIn(restaurant, foodCategories);
        }
    }

    /**
     * @param nameSubstring: optional, may be null. If present, the returned restaurants' names
     *                     must have it
     *                     as a substring. Otherwise, any restaurant name is accepted.
     * @param allowedDeliveryZoneName optional, may be null. If present, the returned
     *                                restaurants' must deliver to the zone with the given name.
     *                                Otherwise, any restaurant is accepted.
     * @return the list of restaurants fulfilling theabove two criteria.
     */
    public List<Restaurant> getFilteredRestaurants(@Nullable String nameSubstring,
                                                   @Nullable String allowedDeliveryZoneName) {
        List<Restaurant> restaurants;

        Optional<DeliveryZone> deliveryZone = Optional.empty();
        if (allowedDeliveryZoneName != null) {
            deliveryZone = deliveryZoneRepository.findByName(allowedDeliveryZoneName);
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

        return restaurants;
    }
}
