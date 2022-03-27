package com.example.food_delivery.service.admin_order_management.order_states;

import com.example.food_delivery.model.FoodOrder;
import com.example.food_delivery.service.admin_order_management.order_states.impl.*;

public class OrderStateFactory {

    public static IOrderState createOrderState(FoodOrder.OrderStatus orderStatus) {
        switch (orderStatus) {
            case PENDING: return new PendingState();
            case ACCEPTED: return new AcceptedState();
            case DECLINED: return new DeclinedState();
            case IN_DELIVERY: return new InDeliveryState();
            case DELIVERED: return new DeliveredState();
        }
        return new PendingState();
    }
}
