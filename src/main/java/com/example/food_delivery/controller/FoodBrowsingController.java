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

    @GetMapping("/get_filtered_restaurants")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public List<RestaurantDTO> getFilteredRestaurants(@RequestParam(required = false) String nameSubString,
                                                      @RequestParam(required = false) String deliveryZoneName) {
        return foodBrowsingService.getFilteredRestaurants(nameSubString, deliveryZoneName);
    }

    @GetMapping("/get_restaurant_menu")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public List<FoodDTO> getRestaurantMenu(@RequestBody RestaurantNameDTO restaurantNameDTO) throws RestaurantNotFoundException {
        return foodBrowsingService.getRestaurantMenu(restaurantNameDTO.getRestaurantName());
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RestaurantFilterDTO {
        private String nameSubstring;
        private String deliveryZoneName;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RestaurantNameDTO {
        private String restaurantName;
    }
}
