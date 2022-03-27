package com.example.food_delivery.service.cart;

import com.example.food_delivery.model.CartItem;
import com.example.food_delivery.model.Customer;
import com.example.food_delivery.model.DTO.CartItemDTO;
import com.example.food_delivery.model.Food;
import com.example.food_delivery.model.Restaurant;
import com.example.food_delivery.repository.CartItemRepository;
import com.example.food_delivery.repository.FoodRepository;
import com.example.food_delivery.repository.RestaurantRepository;
import com.example.food_delivery.service.authentication.AuthenticationService;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToAdminsException;
import com.example.food_delivery.service.cart.exceptions.CartItemsFromMultipleRestaurantsException;
import com.example.food_delivery.service.cart.exceptions.FoodNotFoundException;
import com.example.food_delivery.service.food_browsing.RestaurantNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public void addItemToCart(CartItemDTO cartItemDTO) throws AccessRestrictedToAdminsException, FoodNotFoundException, RestaurantNotFoundException, CartItemsFromMultipleRestaurantsException {
        Customer activeUser = authenticationService.getCurrentCustomer();

        // get restaurant of the menu item
        Optional<Restaurant> optRestaurant =
                restaurantRepository.findByName(cartItemDTO.getRestaurantName());
        if (optRestaurant.isEmpty()) {
            throw new RestaurantNotFoundException();
        }

        // get food corresponding to the new cart item
        Optional<Food> optFood =
                foodRepository.findByNameAndRestaurant(cartItemDTO.getFoodName(),optRestaurant.get());

        // check that the food exists
        if (optFood.isEmpty()) {
            throw new FoodNotFoundException();
        }

        // verify that the user doesn't have any cart items from another restaurant
        if (activeUser.getCartItems().stream().anyMatch(cartItem -> !cartItem.getFood().getRestaurant().equals(optRestaurant.get()))) {
            throw new CartItemsFromMultipleRestaurantsException();
        }

        // find old entity, if any
        Optional<CartItem> optOldCartItem = cartItemRepository.findByCustomerAndFood(activeUser,
                optFood.get());

        // perform update
        if (optOldCartItem.isPresent() && cartItemDTO.getQuantity() == 0) {
            cartItemRepository.deleteById(optOldCartItem.get().getId());
        } else if (optOldCartItem.isPresent()) {
            CartItem oldCartItem = optOldCartItem.get();
            oldCartItem.setQuantity(cartItemDTO.getQuantity());
            cartItemRepository.save(oldCartItem);
        } else if (cartItemDTO.getQuantity() > 0) {
            CartItem cartItem = new CartItem(cartItemDTO.getQuantity(), activeUser, optFood.get());
            cartItemRepository.save(cartItem);
        }
    }
}
