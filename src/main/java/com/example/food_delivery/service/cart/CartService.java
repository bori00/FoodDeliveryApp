package com.example.food_delivery.service.cart;

import com.example.food_delivery.model.CartItem;
import com.example.food_delivery.model.Customer;
import com.example.food_delivery.model.DTO.CartItemDTO;
import com.example.food_delivery.model.DTO.CustomerCartDTO;
import com.example.food_delivery.model.DTO.FoodDTO;
import com.example.food_delivery.model.Food;
import com.example.food_delivery.model.Restaurant;
import com.example.food_delivery.repository.CartItemRepository;
import com.example.food_delivery.repository.FoodRepository;
import com.example.food_delivery.repository.RestaurantRepository;
import com.example.food_delivery.service.authentication.AuthenticationService;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToAdminsException;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToCustomersException;
import com.example.food_delivery.service.authentication.jwt.JwtUtils;
import com.example.food_delivery.service.cart.exceptions.CartItemsFromMultipleRestaurantsException;
import com.example.food_delivery.service.cart.exceptions.FoodNotFoundException;
import com.example.food_delivery.service.food_browsing.exceptions.RestaurantNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service responsible for updating the content of a user's cart and collecting data about the
 * content.
 */
@Service
public class CartService {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ModelMapper mapper;

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    /**
     * Adds a new item to the cart of the active user.
     * @param cartItemDTO is a DTo representing the menu items and the new quantity of it to be
     *                    in the cart of the user.
     * @throws FoodNotFoundException if the requested menu item is not found in the database,
     * @throws RestaurantNotFoundException if the restaurant to which the menu item belongs is
     * not found in the database.
     * @throws CartItemsFromMultipleRestaurantsException if the active user already has menu
     * items from another restaurant in their cart.
     * @throws AccessRestrictedToCustomersException if the active user is not a customer.
     */
    public void addItemToCart(CartItemDTO cartItemDTO) throws FoodNotFoundException, RestaurantNotFoundException, CartItemsFromMultipleRestaurantsException, AccessRestrictedToCustomersException {
        Customer activeUser = authenticationService.getCurrentCustomer();

        // get restaurant of the menu item
        Optional<Restaurant> optRestaurant =
                restaurantRepository.findByName(cartItemDTO.getRestaurantName());
        if (optRestaurant.isEmpty()) {
            logger.error("INVALID UPDATE - the restaurant, whose menu item is to be added to the " +
                    "cart, is not found.");
            throw new RestaurantNotFoundException();
        }

        // get food corresponding to the new cart item
        Optional<Food> optFood =
                foodRepository.findByNameAndRestaurant(cartItemDTO.getFoodName(),optRestaurant.get());

        // check that the food exists
        if (optFood.isEmpty()) {
            logger.error("INVALID UPDATE - the menu item to be added to the cart is not found in " +
                    "the database.");
            throw new FoodNotFoundException();
        }

        // verify that the user doesn't have any cart items from another restaurant
        if (activeUser.getCartItems().stream().anyMatch(cartItem -> !cartItem.getFood().getRestaurant().equals(optRestaurant.get()))) {
            logger.warn(String.format("INVALID UPDATE - customer %s attempted to add menu items " +
                    "from " +
                    "multiple " +
                    "restaurants to their cart.", activeUser.getUserName()));
            throw new CartItemsFromMultipleRestaurantsException();
        }

        // find old entity, if any
        Optional<CartItem> optOldCartItem = cartItemRepository.findByCustomerAndFood(activeUser,
                optFood.get());

        // perform update
        if (optOldCartItem.isPresent() && cartItemDTO.getQuantity() == 0) {
            cartItemRepository.deleteById(optOldCartItem.get().getId());
            logger.info(String.format("UPDATE - deleted menu item %s from the cart of user %s",
                    optOldCartItem.get().getFood().getName(), activeUser.getUserName()));
        } else if (optOldCartItem.isPresent()) {
            CartItem oldCartItem = optOldCartItem.get();
            int prevQuantity = oldCartItem.getQuantity();
            oldCartItem.setQuantity(cartItemDTO.getQuantity());
            cartItemRepository.save(oldCartItem);
            logger.info(String.format("UPDATE - updated menu item %s's quantity in the cart of " +
                            "user %s from %d to %d",
                    optOldCartItem.get().getFood().getName(), activeUser.getUserName(),
                    prevQuantity, cartItemDTO.getQuantity()));
        } else if (cartItemDTO.getQuantity() > 0) {
            CartItem cartItem = new CartItem(cartItemDTO.getQuantity(), activeUser, optFood.get());
            cartItemRepository.save(cartItem);
            logger.info(String.format("UPDATE - added menu item %s in the cart of " +
                            "user %s with quantity %d",
                    cartItemDTO.getFoodName(), activeUser.getUserName(),
                    cartItemDTO.getQuantity()));
        }
    }

    /**
     * Loads and returns the content of the active user's cart.
     * @return the content, i.e. the menu items with their quantity, of the active user.
     * @throws AccessRestrictedToCustomersException if the active user is not a customer.
     */
    public CustomerCartDTO getCustomersCartContent() throws AccessRestrictedToCustomersException {
        Customer activeUser = authenticationService.getCurrentCustomer();

        if (activeUser.getCartItems().isEmpty()) {
            return new CustomerCartDTO(null, null);
        }

        String restaurantName =
                activeUser.getCartItems().iterator().next().getFood().getRestaurant().getName();
        return new CustomerCartDTO(restaurantName,
                activeUser.getCartItems()
                        .stream()
                        .collect(Collectors.toMap(cartItem -> mapper.map(cartItem.getFood(),
                                FoodDTO.class), CartItem::getQuantity))
        );
    }

    /**
     * Finds the quantity of the given menu item placed in the cart of the active user.
     * @param itemName if the menu item's name whose quantity is returned.
     * @param restaurantName is the restaurant's name which offers the menu item. Note that
     *                       multiple restaurants may have menu items with the same name.
     * @return the quantity of the given menu item placed in the cart of the active user
     * @throws AccessRestrictedToCustomersException if the active user is not a customer.
     */
    public Integer getCartItemQuantity(String itemName, String restaurantName) throws AccessRestrictedToCustomersException {
        Customer activeUser = authenticationService.getCurrentCustomer();

        Optional<CartItem> cartItem =
                activeUser.getCartItems()
                        .stream()
                        .filter(item -> item.getFood().getName().equals(itemName) && item.getFood().getRestaurant().getName().equals(restaurantName))
                        .findFirst();

        if (cartItem.isPresent()) {
            return cartItem.get().getQuantity();
        } else {
            return 0;
        }
    }
}
