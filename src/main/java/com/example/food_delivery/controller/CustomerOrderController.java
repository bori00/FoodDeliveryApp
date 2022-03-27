package com.example.food_delivery.controller;

import com.example.food_delivery.model.DTO.CartItemDTO;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToAdminsException;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToCustomersException;
import com.example.food_delivery.service.cart.exceptions.CartItemsFromMultipleRestaurantsException;
import com.example.food_delivery.service.cart.exceptions.FoodNotFoundException;
import com.example.food_delivery.service.customer_order_management.CustomerOrdersService;
import com.example.food_delivery.service.customer_order_management.exceptions.EmptyOrderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class CustomerOrderController {
    @Autowired
    private CustomerOrdersService customerOrdersService;

    @PostMapping("/place_order")
    public void addItemToCart() throws EmptyOrderException, CartItemsFromMultipleRestaurantsException, AccessRestrictedToCustomersException {
        customerOrdersService.placeOrder();
    }
}
