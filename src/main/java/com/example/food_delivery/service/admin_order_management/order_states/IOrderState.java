package com.example.food_delivery.service.admin_order_management.order_states;

import com.example.food_delivery.model.FoodOrder;
import com.example.food_delivery.service.admin_order_management.order_states.exceptions.InvalidOrderStatusChangeException;

import java.util.Collection;
import java.util.List;

/**
 * Interface representing a state of an Order.
 */
public interface IOrderState {

    /**
     * If the transition from the current state is possible, sets the status of order to
     * the new status.
     * @param status is the new status to be set.
     * @param order the is order whose status is to be changed.
     * @throws InvalidOrderStatusChangeException if the requested transition is invalid.
     */
    default void transitionTo(FoodOrder.OrderStatus status, FoodOrder order) throws InvalidOrderStatusChangeException {
        if (getAllowedTransitions().contains(status)) {
            order.setOrderStatus(status);
        } else {
            throw new InvalidOrderStatusChangeException(getCorrespondingStatus(), status);
        }
    }

    /**
     * Finds and returns the collection of states to which a transition is allowed.
     * @return the collection specified above.
     */
    Collection<FoodOrder.OrderStatus> getAllowedTransitions();

    /**
     * @return the OrderStatus, used for persistence and json-based communication, corresponding
     * to this state.
     */
    FoodOrder.OrderStatus getCorrespondingStatus();
}
