package com.example.food_delivery.service.restaurant_management;

import com.example.food_delivery.model.*;
import com.example.food_delivery.model.DTO.FoodDTO;
import com.example.food_delivery.model.DTO.RestaurantDTO;
import com.example.food_delivery.repository.DeliveryZoneRepository;
import com.example.food_delivery.repository.FoodCategoryRepository;
import com.example.food_delivery.repository.FoodRepository;
import com.example.food_delivery.repository.RestaurantRepository;
import com.example.food_delivery.service.authentication.AuthenticationService;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToAdminsException;
import com.example.food_delivery.service.filtering.FilteringFacadeService;
import com.example.food_delivery.service.restaurant_management.exceptions.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RestaurantManagementService {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private DeliveryZoneRepository deliveryZoneRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private FoodCategoryRepository foodCategoryRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private FilteringFacadeService filteringFacadeService;

    public void createRestaurant(RestaurantDTO restaurantDTO) throws AccessRestrictedToAdminsException, MissingAvailableDeliveryZoneException, DuplicateRestaurantNameException, MoreThanOneRestaurantPerAdminException {
        // get active user
        RestaurantAdmin admin = authenticationService.getCurrentAdmin();

        // verify that the user doesn't have another restaurant set up already
        if (admin.getRestaurant() != null) {
            throw new MoreThanOneRestaurantPerAdminException();
        }

        if (restaurantDTO.getAvailableDeliveryZones() == null) {
            restaurantDTO.setAvailableDeliveryZones(new HashSet<>());
        }

        // find available delivery zones, ensure that there's at least one of them
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

        // save restaurant
        Restaurant restaurant = new Restaurant(restaurantDTO.getName(),
                restaurantDTO.getAddress(),
                admin,
                deliveryZones);
        restaurantRepository.save(restaurant);
    }

    public void addFoodToMenu(FoodDTO foodDTO) throws AccessRestrictedToAdminsException, InvalidFoodCategoryException, DuplicateFoodNameInsideRestaurantException, NoRestaurantSetupForAdminException {
        // get active user
        RestaurantAdmin admin = authenticationService.getCurrentAdmin();

        // get the restaurant of the active user
        Restaurant restaurant = admin.getRestaurant();
        if (restaurant == null) {
            throw new NoRestaurantSetupForAdminException();
        }

        // get the food category
        Optional<FoodCategory> optFoodCategory =
                foodCategoryRepository.findByName(foodDTO.getFoodCategory());
        if (optFoodCategory.isEmpty()) {
            throw new InvalidFoodCategoryException();
        }

        // check that there is no menu item with the same name in the restaurant
        if (restaurant.getFoods().stream().anyMatch(food -> food.getName().equals(foodDTO.getName()))) {
            throw new DuplicateFoodNameInsideRestaurantException();
        }

        Food food = mapper.map(foodDTO, Food.class);
        food.setFoodCategory(optFoodCategory.get());
        food.setRestaurant(restaurant);

        foodRepository.save(food);
    }

    public List<FoodDTO> getActiveAdminsRestaurantsMenu(List<String> filterFoodCategoryNames) throws AccessRestrictedToAdminsException, NoRestaurantSetupForAdminException {
        // get active user
        RestaurantAdmin admin = authenticationService.getCurrentAdmin();

        // get the restaurant of the active user
        Restaurant restaurant = admin.getRestaurant();
        if (restaurant == null) {
            throw new NoRestaurantSetupForAdminException();
        }

        List<Food> foods = filteringFacadeService.getFilteredFoods(restaurant,
                filterFoodCategoryNames);

        return foods.stream().map(food -> mapper.map(food, FoodDTO.class)).collect(Collectors.toList());
    }
}
