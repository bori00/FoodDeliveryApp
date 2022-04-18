package com.example.food_delivery.service.admin_order_management.order_states;

import com.example.food_delivery.model.FoodOrder;
import com.example.food_delivery.service.admin_order_management.order_states.impl.*;

/**
 * A Factory that builds the IOrderState corresponding to an OrderStatus.
 *
 * Note that IOrderStates are used in the business logic, whereas OrderStatuses are used for
 * persisting data in the database and for sending data in a serialized format.
 */
public class OrderStateFactory {

    /**
     * Converts an orderStatus to the corresponding IOrderState.
     * @param orderStatus is the status to converted
     * @return the converted IOrderState.
     */
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
