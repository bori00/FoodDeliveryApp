package com.example.food_delivery.controller;

import com.example.food_delivery.model.DTO.FoodDTO;
import com.example.food_delivery.model.DTO.RestaurantDTO;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToAdminsException;
import com.example.food_delivery.service.restaurant_management.RestaurantManagementService;
import com.example.food_delivery.service.restaurant_management.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class RestaurantManagementController {

    @Autowired
    private RestaurantManagementService restaurantManagementService;

    private static final Logger logger = LoggerFactory.getLogger(MenuAccessController.class);

    @PostMapping("/add_restaurant")
    @PreAuthorize("hasAuthority('RESTAURANT_ADMIN')")
    public void addRestaurant(@Valid @RequestBody RestaurantDTO restaurantDTO) throws AccessRestrictedToAdminsException, MissingAvailableDeliveryZoneException, DuplicateRestaurantNameException, MoreThanOneRestaurantPerAdminException {
        logger.info(String.format("REQUEST - /add_restaurant with DTO %s", restaurantDTO));
        restaurantManagementService.createRestaurant(restaurantDTO);
    }

    @PostMapping("/add_food_to_menu")
    @PreAuthorize("hasAuthority('RESTAURANT_ADMIN')")
    public void addFoodToMenu(@Valid @RequestBody FoodDTO foodDTO) throws AccessRestrictedToAdminsException, InvalidFoodCategoryException, DuplicateFoodNameInsideRestaurantException, NoRestaurantSetupForAdminException {
        logger.info(String.format("REQUEST - /add_food-to_menu with DTO %s", foodDTO));
        restaurantManagementService.addFoodToMenu(foodDTO);
    }

    @GetMapping("/get_my_menu")
    @PreAuthorize("hasAuthority('RESTAURANT_ADMIN')")
    public List<FoodDTO> getActiveAdminsRestaurantsMenu(@RequestParam List<String> filterFoodCategoryNames) throws AccessRestrictedToAdminsException, NoRestaurantSetupForAdminException {
        logger.info(String.format("REQUEST - /get_my_menu with category names %s", filterFoodCategoryNames));
        return restaurantManagementService.getActiveAdminsRestaurantsMenu(filterFoodCategoryNames);
    }
}
