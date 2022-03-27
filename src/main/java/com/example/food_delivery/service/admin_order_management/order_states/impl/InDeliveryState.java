package com.example.food_delivery.service.admin_order_management.order_states.impl;

import com.example.food_delivery.model.FoodOrder;
import com.example.food_delivery.service.admin_order_management.order_states.IOrderState;

import java.util.Collection;
import java.util.List;

public class InDeliveryState implements IOrderState {

    @Override
    public Collection<FoodOrder.OrderStatus> getAllowedTransitions() {
        return List.of(FoodOrder.OrderStatus.DELIVERED);
    }

    @Override
    public FoodOrder.OrderStatus getCorrespondingStatus() {
        return FoodOrder.OrderStatus.IN_DELIVERY;
    }
}
