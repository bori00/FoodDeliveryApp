package com.example.food_delivery.service.admin_order_management.order_states;

import com.example.food_delivery.model.FoodOrder;
import com.example.food_delivery.service.admin_order_management.order_states.exceptions.InvalidOrderStatusChangeException;

import java.util.Collection;
import java.util.List;

public interface IOrderState {

    default void transitionTo(FoodOrder.OrderStatus status, FoodOrder order) throws InvalidOrderStatusChangeException {
        if (getAllowedTransitions().contains(status)) {
            order.setOrderStatus(status);
        } else {
            throw new InvalidOrderStatusChangeException(getCorrespondingStatus(), status);
        }
    }

    Collection<FoodOrder.OrderStatus> getAllowedTransitions();

    FoodOrder.OrderStatus getCorrespondingStatus();
}
