package com.example.food_delivery.service.admin_order_management.order_states.exceptions;

import com.example.food_delivery.model.FoodOrder;
import lombok.Getter;

@Getter
public class InvalidOrderStatusChangeException extends Exception {

    private final FoodOrder.OrderStatus prevStatus;

    private final FoodOrder.OrderStatus newStatus;

    public InvalidOrderStatusChangeException(FoodOrder.OrderStatus prevStatus, FoodOrder.OrderStatus newStatus) {
        this.prevStatus = prevStatus;
        this.newStatus = newStatus;
    }
}
