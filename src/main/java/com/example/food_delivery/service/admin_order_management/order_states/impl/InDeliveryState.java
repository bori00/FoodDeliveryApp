package com.example.food_delivery.service.admin_order_management.order_states.impl;

import com.example.food_delivery.model.FoodOrder;
import com.example.food_delivery.service.admin_order_management.order_states.IOrderState;

import java.util.Collection;
import java.util.List;

/**
 * Class representing an order's status which is currently in-delivery.
 *
 * Such an order can be marked as 'delivered' by the admin.
 */
public class InDeliveryState implements IOrderState {

    /** {@inheritDoc} */
    @Override
    public Collection<FoodOrder.OrderStatus> getAllowedTransitions() {
        return List.of(FoodOrder.OrderStatus.DELIVERED);
    }

    /** {@inheritDoc} */
    @Override
    public FoodOrder.OrderStatus getCorrespondingStatus() {
        return FoodOrder.OrderStatus.IN_DELIVERY;
    }
}
