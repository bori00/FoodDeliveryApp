package com.example.food_delivery.controller;

import com.example.food_delivery.model.DTO.CartItemDTO;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToAdminsException;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToCustomersException;
import com.example.food_delivery.service.cart.CartService;
import com.example.food_delivery.service.cart.exceptions.CartItemsFromMultipleRestaurantsException;
import com.example.food_delivery.service.cart.exceptions.FoodNotFoundException;
import com.example.food_delivery.service.food_browsing.exceptions.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add_item_to_cart")
    public void addItemToCart(@Valid @RequestBody CartItemDTO cartItemDTO) throws CartItemsFromMultipleRestaurantsException, FoodNotFoundException, RestaurantNotFoundException, AccessRestrictedToCustomersException {
        cartService.addItemToCart(cartItemDTO);
    }

}
