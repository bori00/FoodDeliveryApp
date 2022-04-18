package com.example.food_delivery.service.admin_order_management.order_states.impl;

import com.example.food_delivery.model.FoodOrder;
import com.example.food_delivery.service.admin_order_management.order_states.IOrderState;
import com.example.food_delivery.service.admin_order_management.order_states.exceptions.InvalidOrderStatusChangeException;

import java.util.Collection;
import java.util.List;

/**
 * Class representing a pending order's status.
 *
 * A pending order can be accepted or declines by the restaurant's order.
 */
public class PendingState implements IOrderState {

    /** {@inheritDoc} */
    @Override
    public Collection<FoodOrder.OrderStatus> getAllowedTransitions() {
        return List.of(FoodOrder.OrderStatus.ACCEPTED, FoodOrder.OrderStatus.DECLINED);
    }

    /** {@inheritDoc} */
    @Override
    public FoodOrder.OrderStatus getCorrespondingStatus() {
        return FoodOrder.OrderStatus.PENDING;
    }
}
