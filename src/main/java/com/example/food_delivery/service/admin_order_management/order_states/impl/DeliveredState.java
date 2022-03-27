package com.example.food_delivery.service.admin_order_management.order_states.impl;

import com.example.food_delivery.model.FoodOrder;
import com.example.food_delivery.service.admin_order_management.order_states.IOrderState;
import com.example.food_delivery.service.admin_order_management.order_states.exceptions.InvalidOrderStatusChangeException;

import java.util.Collection;
import java.util.List;

public class DeliveredState implements IOrderState {

    @Override
    public Collection<FoodOrder.OrderStatus> getAllowedTransitions() {
        return List.of();
    }

    @Override
    public FoodOrder.OrderStatus getCorrespondingStatus() {
        return FoodOrder.OrderStatus.DELIVERED;
    }
}
