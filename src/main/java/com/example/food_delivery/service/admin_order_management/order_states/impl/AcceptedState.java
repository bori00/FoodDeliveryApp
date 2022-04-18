package com.example.food_delivery.service.admin_order_management.order_states.impl;

import com.example.food_delivery.model.FoodOrder;
import com.example.food_delivery.service.admin_order_management.order_states.IOrderState;
import com.example.food_delivery.service.admin_order_management.order_states.exceptions.InvalidOrderStatusChangeException;

import java.util.Collection;
import java.util.List;

/**
 * Class representing an accepted order's status.
 *
 * An admin can mark such an order as being 'in-delivery'.
 */
public class AcceptedState implements IOrderState {

    /** {@inheritDoc} */
    @Override
    public Collection<FoodOrder.OrderStatus> getAllowedTransitions() {
        return List.of(FoodOrder.OrderStatus.IN_DELIVERY);
    }

    /** {@inheritDoc} */
    @Override
    public FoodOrder.OrderStatus getCorrespondingStatus() {
        return FoodOrder.OrderStatus.ACCEPTED;
    }
}
