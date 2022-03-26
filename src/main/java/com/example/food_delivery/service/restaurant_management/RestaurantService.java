package com.example.food_delivery.service.restaurant_management;

import com.example.food_delivery.model.DTO.RestaurantDTO;
import com.example.food_delivery.model.DeliveryZone;
import com.example.food_delivery.model.Restaurant;
import com.example.food_delivery.model.RestaurantAdmin;
import com.example.food_delivery.repository.DeliveryZoneRepository;
import com.example.food_delivery.repository.RestaurantRepository;
import com.example.food_delivery.service.authentication.AuthenticationService;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToAdminsException;
import com.example.food_delivery.service.restaurant_management.exceptions.DuplicateRestaurantNameException;
import com.example.food_delivery.service.restaurant_management.exceptions.MissingAvailableDeliveryZoneException;
import com.example.food_delivery.service.restaurant_management.exceptions.MoreThanOneRestaurantPerAdminException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class RestaurantService {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private DeliveryZoneRepository deliveryZoneRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public void createRestaurant(RestaurantDTO restaurantDTO) throws AccessRestrictedToAdminsException, MissingAvailableDeliveryZoneException, DuplicateRestaurantNameException, MoreThanOneRestaurantPerAdminException {
        // get active user
        RestaurantAdmin admin = authenticationService.getCurrentAdmin();

        // verify that the user doesn't have another restaurant set up already
        if (admin.getRestaurant() != null) {
            throw new MoreThanOneRestaurantPerAdminException();
        }

        Set<DeliveryZone> deliveryZones =
                deliveryZoneRepository.findAllByNameIn(restaurantDTO.getAvailableDeliveryZones());

        if (deliveryZones.isEmpty()) {
            throw new MissingAvailableDeliveryZoneException();
        }

        // check that no other restaurant with the same name exists
        Optional<Restaurant> restaurantWithSameName =
                restaurantRepository.findByName(restaurantDTO.getName());
        if (restaurantWithSameName.isPresent()) {
            throw new DuplicateRestaurantNameException();
        }

        Restaurant restaurant = new Restaurant(restaurantDTO.getName(),
                restaurantDTO.getAddress(),
                admin,
                deliveryZones);

        restaurantRepository.save(restaurant);

    }
}
