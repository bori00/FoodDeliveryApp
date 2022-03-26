package com.example.food_delivery.controller;

import com.example.food_delivery.model.DTO.FoodDTO;
import com.example.food_delivery.model.DTO.RestaurantDTO;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToAdminsException;
import com.example.food_delivery.service.restaurant_management.RestaurantService;
import com.example.food_delivery.service.restaurant_management.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/add_restaurant")
    public void addRestaurant(@Valid @RequestBody RestaurantDTO restaurantDTO) throws AccessRestrictedToAdminsException, MissingAvailableDeliveryZoneException, DuplicateRestaurantNameException, MoreThanOneRestaurantPerAdminException {
        restaurantService.createRestaurant(restaurantDTO);
    }

    @PostMapping("/add_food_to_menu")
    public void addFoodToMenu(@Valid @RequestBody FoodDTO foodDTO) throws AccessRestrictedToAdminsException, InvalidFoodCategoryException, DuplicateFoodNameInsideRestaurantException, NoRestaurantSetupForAdminException {
        restaurantService.addFoodToMenu(foodDTO);
    }

    @GetMapping("/get_my_menu")
    public List<FoodDTO> getActiveAdminsRestaurantsMenu() throws AccessRestrictedToAdminsException, NoRestaurantSetupForAdminException {
        return restaurantService.getActiveAdminsRestaurantsMenu();
    }
}
