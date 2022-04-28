package com.example.food_delivery.controller;

import com.example.food_delivery.model.DTO.CartItemDTO;
import com.example.food_delivery.model.DTO.CustomerOrderDTO;
import com.example.food_delivery.model.FoodOrder;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToAdminsException;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToCustomersException;
import com.example.food_delivery.service.cart.exceptions.CartItemsFromMultipleRestaurantsException;
import com.example.food_delivery.service.cart.exceptions.FoodNotFoundException;
import com.example.food_delivery.service.customer_order_management.CustomerOrdersService;
import com.example.food_delivery.service.customer_order_management.exceptions.EmptyOrderException;
import com.example.food_delivery.service.mailing_service.EmailTextGeneratorService;
import com.example.food_delivery.service.mailing_service.MailingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CustomerOrderController {
    @Autowired
    private CustomerOrdersService customerOrdersService;

    @Autowired
    private EmailTextGeneratorService emailTextGeneratorService;

    @Autowired
    MailingService mailingService;

    @PostMapping("/place_order")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public void placeOrder() throws EmptyOrderException, CartItemsFromMultipleRestaurantsException,
            AccessRestrictedToCustomersException {
        FoodOrder order = customerOrdersService.placeOrder();

        String foodOrderEmailReport = emailTextGeneratorService.generateOrderEmailReport(order);

        mailingService.sendSimpleMessage(order.getRestaurant().getAdmin().getEmailAddress(), "New Order",
                foodOrderEmailReport);
    }

    @GetMapping("/get_my_order_history")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public List<CustomerOrderDTO> getMyOrderHistory() throws AccessRestrictedToCustomersException {
        return customerOrdersService.getActiveCustomersOrderHistory();
    }
}
