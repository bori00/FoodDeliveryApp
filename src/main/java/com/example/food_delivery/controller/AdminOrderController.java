package com.example.food_delivery.controller;

import com.example.food_delivery.model.DTO.AdminOrderDTO;
import com.example.food_delivery.model.DTO.CustomerOrderDTO;
import com.example.food_delivery.model.FoodOrder;
import com.example.food_delivery.service.admin_order_management.AdminOrderService;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToAdminsException;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToCustomersException;
import com.example.food_delivery.service.restaurant_management.exceptions.NoRestaurantSetupForAdminException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RestController
public class AdminOrderController {

    @Autowired
    private AdminOrderService adminOrderService;

    @GetMapping("/see_restaurants_filtered_orders")
    public List<AdminOrderDTO> getFilteredRestaurantsOrders(@RequestBody List<FoodOrder.OrderStatus> statuses) throws AccessRestrictedToAdminsException, NoRestaurantSetupForAdminException {
        return adminOrderService.getFilteredRestaurantsOrders(statuses);
    }

    @GetMapping("/see_restaurants_orders")
    public List<AdminOrderDTO> getRestaurantsOrders() throws AccessRestrictedToAdminsException, NoRestaurantSetupForAdminException {
        return adminOrderService.getFilteredRestaurantsOrders(Arrays.asList(FoodOrder.OrderStatus.values()));
    }
}
