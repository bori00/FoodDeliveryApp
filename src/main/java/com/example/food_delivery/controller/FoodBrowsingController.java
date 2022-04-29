package com.example.food_delivery.controller;

import com.example.food_delivery.model.DTO.FoodDTO;
import com.example.food_delivery.model.DTO.RestaurantDTO;
import com.example.food_delivery.model.DeliveryZone;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToAdminsException;
import com.example.food_delivery.service.food_browsing.FoodBrowsingService;
import com.example.food_delivery.service.food_browsing.exceptions.RestaurantNotFoundException;
import com.example.food_delivery.service.restaurant_management.exceptions.NoRestaurantSetupForAdminException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
public class FoodBrowsingController {

    @Autowired
    private FoodBrowsingService foodBrowsingService;

    private static final Logger logger = LoggerFactory.getLogger(FoodBrowsingController.class);

    @GetMapping("/get_filtered_restaurants")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public List<RestaurantDTO> getFilteredRestaurants(@RequestParam(required = false) String nameSubString,
                                                      @RequestParam(required = false) String deliveryZoneName) {
        logger.info(String.format("REQUEST - /get_filtered_restaurants for nameSubString %s and " +
                "deliveryZone %s", nameSubString, deliveryZoneName));
        return foodBrowsingService.getFilteredRestaurants(nameSubString, deliveryZoneName);
    }

    @GetMapping("/get_restaurant_menu")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public List<FoodDTO> getRestaurantMenu(@RequestParam String restaurantName,
                                           @RequestParam List<String> filterFoodCategoryNames) throws RestaurantNotFoundException {
        logger.info(String.format("REQUEST - /get_restaurant_menu for restaurant %s and " +
                "categories %s", restaurantName, filterFoodCategoryNames));
        return foodBrowsingService.getRestaurantMenu(restaurantName, filterFoodCategoryNames);
    }
}
