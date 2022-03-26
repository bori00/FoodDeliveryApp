package com.example.food_delivery.controller;

import com.example.food_delivery.model.DTO.RestaurantDTO;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToAdminsException;
import com.example.food_delivery.service.restaurant_management.RestaurantService;
import com.example.food_delivery.service.restaurant_management.exceptions.DuplicateRestaurantNameException;
import com.example.food_delivery.service.restaurant_management.exceptions.MissingAvailableDeliveryZoneException;
import com.example.food_delivery.service.restaurant_management.exceptions.MoreThanOneRestaurantPerAdminException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/add_restaurant")
    public void addRestaurant(@Valid @RequestBody RestaurantDTO restaurantDTO) throws AccessRestrictedToAdminsException, MissingAvailableDeliveryZoneException, DuplicateRestaurantNameException, MoreThanOneRestaurantPerAdminException {
        restaurantService.createRestaurant(restaurantDTO);
    }
}
