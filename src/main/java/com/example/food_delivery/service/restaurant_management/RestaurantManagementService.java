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
import com.example.food_delivery.service.mailing_service.MailingService;
import com.example.food_delivery.service.restaurant_management.exceptions.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service that allows admins to manage their own restaurants.
 */
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

    private static final Logger logger = LoggerFactory.getLogger(RestaurantManagementService.class);

    /**
     * Creates a new restaurant for the active admin.
     * @param restaurantDTO holds the data of the new restaurant to be created.
     * @throws AccessRestrictedToAdminsException if the current user is not an admin.
     * @throws MissingAvailableDeliveryZoneException if the new restaurant has no available
     * delivery zones.
     * @throws DuplicateRestaurantNameException if another restaurant with the same name already
     * exists.
     * @throws MoreThanOneRestaurantPerAdminException if the active admi already has a restaurant.
     */
    public void createRestaurant(RestaurantDTO restaurantDTO) throws AccessRestrictedToAdminsException, MissingAvailableDeliveryZoneException, DuplicateRestaurantNameException, MoreThanOneRestaurantPerAdminException {
        // get active user
        RestaurantAdmin admin = authenticationService.getCurrentAdmin();

        // verify that the user doesn't have another restaurant set up already
        if (admin.getRestaurant() != null) {
            logger.warn(String.format("INVALID UPDATE - the active admin %s attempted to set up a" +
                    " second restaurant.", admin.getUserName()));
            throw new MoreThanOneRestaurantPerAdminException();
        }

        if (restaurantDTO.getAvailableDeliveryZones() == null) {
            logger.warn(String.format("INVALID UPDATE - the active admin %s attempted to set up a" +
                    " restaurant with no available delivery zones.", admin.getUserName()));
            restaurantDTO.setAvailableDeliveryZones(new HashSet<>());
        }

        // find available delivery zones, ensure that there's at least one of them
        Set<DeliveryZone> deliveryZones =
                deliveryZoneRepository.findAllByNameIn(restaurantDTO.getAvailableDeliveryZones());
        if (deliveryZones.isEmpty()) {
            logger.warn(String.format("INVALID UPDATE - the active admin %s attempted to set up a" +
                    " restaurant with no available delivery zones.", admin.getUserName()));
            throw new MissingAvailableDeliveryZoneException();
        }

        // check that no other restaurant with the same name exists
        Optional<Restaurant> restaurantWithSameName =
                restaurantRepository.findByName(restaurantDTO.getName());
        if (restaurantWithSameName.isPresent()) {
            logger.warn(String.format("INVALID UPDATE - the active admin %s attempted to set up a" +
                    " restaurant with name %s, which is already taken.", admin.getUserName(),
                    restaurantDTO.getName()));
            throw new DuplicateRestaurantNameException();
        }

        // save restaurant
        Restaurant restaurant = new Restaurant(restaurantDTO.getName(),
                restaurantDTO.getAddress(),
                admin,
                deliveryZones);
        restaurantRepository.save(restaurant);

        logger.info(String.format("UPDATE - saved new restaurant with name %s for admin %s",
                restaurant.getName(), admin.getUserName()));
    }

    /**
     * Adds a new menu item to the restaurant of the active user.
     * @param foodDTO holds the data of the new menu item.
     * @throws AccessRestrictedToAdminsException if the active user is not an admin.
     * @throws InvalidFoodCategoryException if the requested food category (specified in
     * foodDTO) cannot be found.
     * @throws DuplicateFoodNameInsideRestaurantException if the restaurant already has a menu
     * item with the same name.
     * @throws NoRestaurantSetupForAdminException if the active user has no restaurant yet.
     */
    public void addFoodToMenu(FoodDTO foodDTO) throws AccessRestrictedToAdminsException, InvalidFoodCategoryException, DuplicateFoodNameInsideRestaurantException, NoRestaurantSetupForAdminException {
        // get active user
        RestaurantAdmin admin = authenticationService.getCurrentAdmin();

        // get the restaurant of the active user
        Restaurant restaurant = admin.getRestaurant();
        if (restaurant == null) {
            logger.warn(String.format("INVALID UPDATE - the active user %s attempted to add a " +
                    "menu item, but has no restaurant yet", admin.getUserName()));
            throw new NoRestaurantSetupForAdminException();
        }

        // get the food category
        Optional<FoodCategory> optFoodCategory =
                foodCategoryRepository.findByName(foodDTO.getFoodCategory());
        if (optFoodCategory.isEmpty()) {
            logger.warn(String.format("INVALID UPDATE - the active user %s attempted to add a " +
                    "menu item, but the requested food category %s is not found",
                    admin.getUserName(), foodDTO.getFoodCategory()));
            throw new InvalidFoodCategoryException();
        }

        // check that there is no menu item with the same name in the restaurant
        if (restaurant.getFoods().stream().anyMatch(food -> food.getName().equals(foodDTO.getName()))) {
            logger.warn(String.format("INVALID UPDATE - the active user %s attempted to add a " +
                    "menu item with name %s, but the name is taken", admin.getUserName(),
                    foodDTO.getName()));
            throw new DuplicateFoodNameInsideRestaurantException();
        }

        Food food = mapper.map(foodDTO, Food.class);
        food.setFoodCategory(optFoodCategory.get());
        food.setRestaurant(restaurant);

        foodRepository.save(food);

        logger.info(String.format("UPDATE - saved new menu item %s for restaurant %s",
                foodDTO.getName(), restaurant.getName()));
    }

    /**
     * Returns the menu items of the restaurant belonging to the active user, which belong to any
     * of the requested food categories.
     * @param filterFoodCategoryNames is the list of the names of the food categories, whose food
     *                               items are returned.
     * @return the list of the menu items in the active user's restaurant, within the given food
     * categories,
     * @throws AccessRestrictedToAdminsException if the active user is not an admin.
     * @throws NoRestaurantSetupForAdminException if the active admin has no restaurant yet.
     */
    public List<FoodDTO> getActiveAdminsRestaurantsMenu(List<String> filterFoodCategoryNames) throws AccessRestrictedToAdminsException, NoRestaurantSetupForAdminException {
        // get active user
        RestaurantAdmin admin = authenticationService.getCurrentAdmin();

        // get the restaurant of the active user
        Restaurant restaurant = admin.getRestaurant();
        if (restaurant == null) {
            logger.warn(String.format("INVALID REQUEST - the active user %s attempted to access " +
                    "their restaurant's menu, but has no restaurant yet", admin.getUserName()));
            throw new NoRestaurantSetupForAdminException();
        }

        List<Food> foods = filteringFacadeService.getFilteredFoods(restaurant,
                filterFoodCategoryNames);

        return foods.stream().map(food -> mapper.map(food, FoodDTO.class)).collect(Collectors.toList());
    }

    /**
     * Finds the name of the active user's restaurant.
     * @return the name of the active user's restaurant.
     * @throws AccessRestrictedToAdminsException if the active user is not an admin.
     * @throws NoRestaurantSetupForAdminException if the active admin has no restaurant yet.
     */
    public String getActiveAdminsRestaurantsName() throws AccessRestrictedToAdminsException,
            NoRestaurantSetupForAdminException {
        // get active user
        RestaurantAdmin admin = authenticationService.getCurrentAdmin();

        // get the restaurant of the active user
        Restaurant restaurant = admin.getRestaurant();
        if (restaurant == null) {
            logger.warn(String.format("INVALID REQUEST - the active user %s attempted to access " +
                    "their restaurant's name, but has no restaurant yet", admin.getUserName()));
            throw new NoRestaurantSetupForAdminException();
        }

        return restaurant.getName();
    }
}
