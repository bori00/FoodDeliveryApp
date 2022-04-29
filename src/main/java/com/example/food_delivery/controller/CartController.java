package com.example.food_delivery.controller;

import com.example.food_delivery.model.DTO.CartItemDTO;
import com.example.food_delivery.model.DTO.CustomerCartDTO;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToAdminsException;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToCustomersException;
import com.example.food_delivery.service.cart.CartService;
import com.example.food_delivery.service.cart.exceptions.CartItemsFromMultipleRestaurantsException;
import com.example.food_delivery.service.cart.exceptions.FoodNotFoundException;
import com.example.food_delivery.service.food_browsing.exceptions.RestaurantNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @PostMapping("/add_item_to_cart")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public void addItemToCart(@Valid @RequestBody CartItemDTO cartItemDTO) throws CartItemsFromMultipleRestaurantsException, FoodNotFoundException, RestaurantNotFoundException, AccessRestrictedToCustomersException {
        logger.info(String.format("REQUEST - /add_item_to_cart, for cart item %s and" +
                        " quantity %d",
                cartItemDTO.getFoodName(), cartItemDTO.getQuantity()));
        cartService.addItemToCart(cartItemDTO);
    }

    @GetMapping("/get_my_cart")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public CustomerCartDTO getMyCart() throws AccessRestrictedToCustomersException {
        logger.info("REQUEST - /get_my_cart");
        return cartService.getCustomersCartContent();
    }

    @GetMapping("/get_cart_item_quantity")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public Integer getCartItemQuantity(@RequestParam String itemName,
                                       @RequestParam String restaurantName) throws AccessRestrictedToCustomersException {
        logger.info("REQUEST - /get_my_cart");
        return cartService.getCartItemQuantity(itemName, restaurantName);
    }

}
