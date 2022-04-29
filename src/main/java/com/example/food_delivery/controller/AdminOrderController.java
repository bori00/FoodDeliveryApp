package com.example.food_delivery.controller;

import com.example.food_delivery.model.DTO.AdminOrderDTO;
import com.example.food_delivery.model.DTO.CustomerOrderDTO;
import com.example.food_delivery.model.FoodOrder;
import com.example.food_delivery.service.admin_order_management.AdminOrderService;
import com.example.food_delivery.service.admin_order_management.exceptions.RequestedOrderNotFoundException;
import com.example.food_delivery.service.admin_order_management.order_states.exceptions.InvalidOrderStatusChangeException;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToAdminsException;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToCustomersException;
import com.example.food_delivery.service.authentication.jwt.JwtUtils;
import com.example.food_delivery.service.restaurant_management.exceptions.NoRestaurantSetupForAdminException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Controller responsible for the order-related requests sent by a restaurant admin.
 */
@RestController
public class AdminOrderController {

    @Autowired
    private AdminOrderService adminOrderService;

    private static final Logger logger = LoggerFactory.getLogger(AdminOrderController.class);

    /**
     * Handles a request sent by a restaurant admin, and responds by returning the list of orders
     * sent to the restaurant of the active user, which have any of the allowed statuses.
     * @param statuses represents the list of the allowed statuses. If empty, then all statuses
     *                 are considered to be allowed.
     * @return the list of orders sent to the restaurant of the active user, which have any of
     * the allowed statuses.
     * @throws AccessRestrictedToAdminsException if the active user is not an admin.
     * @throws NoRestaurantSetupForAdminException if the active user has no restaurant.
     */
    @GetMapping("/see_restaurants_sorted_filtered_orders")
    @PreAuthorize("hasAuthority('RESTAURANT_ADMIN')")
    public List<AdminOrderDTO> getFilteredRestaurantsOrders(@RequestParam List<FoodOrder.OrderStatus> statuses) throws AccessRestrictedToAdminsException, NoRestaurantSetupForAdminException {
        logger.info("REQUEST - /see_restaurants_sorted_filtered_orders");
        return adminOrderService.getFilteredSortedRestaurantsOrders(statuses);
    }

    /**
     * Handles a request sent by a restaurant admin, by setting the status of the order referred
     * to by the command to the newStatus of the command, assuming that this status transition is
     * allowed.
     * @param command is a DTO specifies the orderId of the order whose status is to be changed,
     *                and the newStatus, representing the new status to be set for the order.
     * @throws InvalidOrderStatusChangeException if the requested status change is not allowed.
     * @throws RequestedOrderNotFoundException if no order with the requested orderId exists.
     * @throws AccessRestrictedToAdminsException if the active user is not an admin.
     */
    @PostMapping("/change_order_status")
    @PreAuthorize("hasAuthority('RESTAURANT_ADMIN')")
    public void changeOrderStatus(@RequestBody OrderStatusChangeCommand command) throws InvalidOrderStatusChangeException,
            RequestedOrderNotFoundException, AccessRestrictedToAdminsException {
        logger.info(String.format("REQUEST - /change_order_status, for order %d to status %s",
                command.orderId, command.newStatus));
        adminOrderService.updateOrderState(command.orderId, command.newStatus);
    }

    /**
     * DTO class representing the details of a command for changing the status of an order.
     */
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderStatusChangeCommand {
        private Long orderId;
        private FoodOrder.OrderStatus newStatus;
    }
}
