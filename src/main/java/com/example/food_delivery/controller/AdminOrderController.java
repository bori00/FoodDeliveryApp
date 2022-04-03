package com.example.food_delivery.controller;

import com.example.food_delivery.model.DTO.AdminOrderDTO;
import com.example.food_delivery.model.DTO.CustomerOrderDTO;
import com.example.food_delivery.model.FoodOrder;
import com.example.food_delivery.service.admin_order_management.AdminOrderService;
import com.example.food_delivery.service.admin_order_management.exceptions.RequestedOrderNotFoundException;
import com.example.food_delivery.service.admin_order_management.order_states.exceptions.InvalidOrderStatusChangeException;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToAdminsException;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToCustomersException;
import com.example.food_delivery.service.restaurant_management.exceptions.NoRestaurantSetupForAdminException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RestController
public class AdminOrderController {

    @Autowired
    private AdminOrderService adminOrderService;

    @GetMapping("/see_restaurants_sorted_filtered_orders")
    @PreAuthorize("hasAuthority('RESTAURANT_ADMIN')")
    public List<AdminOrderDTO> getFilteredRestaurantsOrders(@RequestParam List<FoodOrder.OrderStatus> statuses) throws AccessRestrictedToAdminsException, NoRestaurantSetupForAdminException {
        return adminOrderService.getFilteredSortedRestaurantsOrders(statuses);
    }

    @PostMapping("/change_order_status")
    @PreAuthorize("hasAuthority('RESTAURANT_ADMIN')")
    public void changeOrderStatus(@RequestBody OrderStatusChangeCommand command) throws InvalidOrderStatusChangeException,
            RequestedOrderNotFoundException, AccessRestrictedToAdminsException {
        adminOrderService.updateOrderState(command.orderId, command.newStatus);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderStatusChangeCommand {
        private Long orderId;
        private FoodOrder.OrderStatus newStatus;
    }
}
