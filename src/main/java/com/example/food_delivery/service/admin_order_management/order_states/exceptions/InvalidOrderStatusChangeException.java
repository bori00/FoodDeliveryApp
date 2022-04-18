package com.example.food_delivery.service.admin_order_management.order_states.exceptions;

import com.example.food_delivery.model.FoodOrder;
import lombok.Getter;

/**
 * Exception thrown when a restaurant admin attempts to change the status of an order to a
 * certain status, but the requested transition is not allowed,
 */
@Getter
public class InvalidOrderStatusChangeException extends Exception {

    private final FoodOrder.OrderStatus prevStatus;

    private final FoodOrder.OrderStatus newStatus;

    /**
     * Builds an exception.
     * @param prevStatus is the current status of the order.
     * @param newStatus is the requested new status of the order.
     */
    public InvalidOrderStatusChangeException(FoodOrder.OrderStatus prevStatus, FoodOrder.OrderStatus newStatus) {
        this.prevStatus = prevStatus;
        this.newStatus = newStatus;
    }
}
