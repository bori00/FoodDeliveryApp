package com.example.food_delivery.service.admin_order_management;

import com.example.food_delivery.model.DTO.AdminOrderDTO;
import com.example.food_delivery.model.DTO.FoodDTO;
import com.example.food_delivery.model.FoodOrder;
import com.example.food_delivery.model.OrderItem;
import com.example.food_delivery.model.RestaurantAdmin;
import com.example.food_delivery.repository.FoodOrderRepository;
import com.example.food_delivery.repository.RestaurantRepository;
import com.example.food_delivery.service.admin_order_management.exceptions.RequestedOrderNotFoundException;
import com.example.food_delivery.service.admin_order_management.order_states.OrderStateFactory;
import com.example.food_delivery.service.admin_order_management.order_states.exceptions.InvalidOrderStatusChangeException;
import com.example.food_delivery.service.authentication.AuthenticationService;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToAdminsException;
import com.example.food_delivery.service.restaurant_management.exceptions.MoreThanOneRestaurantPerAdminException;
import com.example.food_delivery.service.restaurant_management.exceptions.NoRestaurantSetupForAdminException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service that implements functionalities related to managing a restaurant's orders, from the
 * admin's perspectives.
 */
@Service
public class AdminOrderService {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private FoodOrderRepository foodOrderRepository;

    @Autowired
    private ModelMapper mapper;

    Logger logger = LoggerFactory.getLogger(AdminOrderService.class);

    /**
     * Returns the restaurant orders from the currently logged-in admin's restaurant, which have
     * any of the specified statuses, sorted reversely by their creation date.
     * @param statuses is the collection of allowed order statuses. If empty, any status is
     *                 considered allowed.
     * @return the list of orders, in AdminOrderDTO format, fulfilling the above specified criteria.
     * @throws AccessRestrictedToAdminsException if the currently logged in user is not an admin.
     * @throws NoRestaurantSetupForAdminException if the currently logged in user does not have a
     * restaurant.
     */
    public List<AdminOrderDTO> getFilteredSortedRestaurantsOrders(Collection<FoodOrder.OrderStatus> statuses) throws AccessRestrictedToAdminsException, NoRestaurantSetupForAdminException {
        // get active user
        RestaurantAdmin admin = authenticationService.getCurrentAdmin();

        // verify that the user does have a restaurant
        if (admin.getRestaurant() == null) {
            logger.warn(String.format("INVALID ACCESS - user %s attempted to get their " +
                    "restaurant orders, but has no restaurant", admin.getUserName()));
            throw new NoRestaurantSetupForAdminException();
        }

        if (statuses.isEmpty()) {
            statuses = Arrays.asList(FoodOrder.OrderStatus.values());
        }

        return foodOrderRepository.findAllByOrderStatusInAndRestaurantOrderByDateTimeDesc(statuses,
                admin.getRestaurant())
                .stream()
                .map(order -> new AdminOrderDTO(
                            order.getId(),
                            order.getCustomer().getUserName(),
                            order.getOrderStatus().toString(),
                            order.getDateTime(),
                            order.getOrderItems().stream()
                                    .collect(Collectors.toMap(
                                            orderItem -> mapper.map(orderItem.getFood(), FoodDTO.class),
                                            OrderItem::getQuantity)))
                        )
                .collect(Collectors.toList());
    }

    /**
     * Updates the status of the order with the given id, to newStatus.
     * @param orderId is the identifier of the order whose state is to be updated.
     * @param newStatus is the status to be set for the order.
     * @return the updated order, with the newStatus.
     * @throws AccessRestrictedToAdminsException if the currently logged in user is not an admin.
     * @throws RequestedOrderNotFoundException if no order with the given id is found.
     * @throws InvalidOrderStatusChangeException if the requested status transition is not allowed.
     */
    public FoodOrder updateOrderState(Long orderId, FoodOrder.OrderStatus newStatus) throws AccessRestrictedToAdminsException, RequestedOrderNotFoundException, InvalidOrderStatusChangeException {
        // get active user
        RestaurantAdmin admin = authenticationService.getCurrentAdmin();

        // get the order to update
        Optional<FoodOrder> optOrder = foodOrderRepository.findById(orderId);

        if (optOrder.isEmpty()) {
            logger.warn(String.format("INVALID REQUEST PARAMS - attempted to update the state of " +
                    "order nr %d, but the order does not exist", orderId));
            throw new RequestedOrderNotFoundException();
        }
        FoodOrder order = optOrder.get();

        // verify that the current user owns the restaurant
        if (!order.getRestaurant().equals(admin.getRestaurant())) {
            logger.warn(String.format("INVALID ACCESS - user %s attempted to update the state of " +
                    "order %d, but the order does not belong to their restaurant",
                    admin.getUserName(), orderId));
            throw new AccessRestrictedToAdminsException();
        }

        FoodOrder.OrderStatus prevStatus = order.getOrderStatus();

        OrderStateFactory
                .createOrderState(order.getOrderStatus())
                .transitionTo(newStatus, order);

        logger.info(String.format("UPDATE - Status of order %d changed from %s to %s",
                order.getId(), prevStatus, newStatus));

        return foodOrderRepository.save(order);
    }
}
