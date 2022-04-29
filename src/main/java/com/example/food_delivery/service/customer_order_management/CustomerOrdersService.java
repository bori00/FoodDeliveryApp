package com.example.food_delivery.service.customer_order_management;

import com.example.food_delivery.model.*;
import com.example.food_delivery.model.DTO.CustomerOrderDTO;
import com.example.food_delivery.model.DTO.FoodDTO;
import com.example.food_delivery.repository.CartItemRepository;
import com.example.food_delivery.repository.FoodOrderRepository;
import com.example.food_delivery.service.authentication.AuthenticationService;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToAdminsException;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToCustomersException;
import com.example.food_delivery.service.cart.CartService;
import com.example.food_delivery.service.cart.exceptions.CartItemsFromMultipleRestaurantsException;
import com.example.food_delivery.service.customer_order_management.exceptions.EmptyOrderException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service that handles order-related actions for customers.
 */
@Service
public class CustomerOrdersService {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private FoodOrderRepository foodOrderRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ModelMapper mapper;

    private static final Logger logger = LoggerFactory.getLogger(CustomerOrdersService.class);

    /**
     * Places a new order for the active user, with the content being the current content of
     * their cart, and sets the cart to empty.
     * @return the order that was placed.
     * @throws EmptyOrderException if the user has no items in their cart.
     * @throws CartItemsFromMultipleRestaurantsException if the user has items from multiple
     * restaurants in their cart.
     * @throws AccessRestrictedToCustomersException if the active user is not a customer.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public FoodOrder placeOrder() throws EmptyOrderException, CartItemsFromMultipleRestaurantsException,
            AccessRestrictedToCustomersException {
        // find the user who places the order
        Customer customer = authenticationService.getCurrentCustomer();

        // find the items to be ordered
        Set<CartItem> cartItems = customer.getCartItems();

        // ensure the order is non-empty
        if (cartItems.isEmpty()) {
            logger.warn(String.format("INVALID UPDATE - user %s attempted to place an empty order",
                    customer.getUserName()));
            throw new EmptyOrderException();
        }

        // find the restaurant to which the order refers
        Set<Restaurant> itemRestaurants = cartItems.stream()
                                                    .map(cartItem -> cartItem.getFood().getRestaurant())
                                                    .collect(Collectors.toSet());

        // ensure all items are from the same restaurant
        if (itemRestaurants.size() > 1) {
            logger.warn(String.format("INVALID UPDATE - user %s attempted to place an order with " +
                            "items from multiple different restaurants",
                    customer.getUserName()));
            throw new CartItemsFromMultipleRestaurantsException();
        }
        Restaurant restaurant = itemRestaurants.iterator().next();

        // build the Order
        FoodOrder foodOrder = new FoodOrder(FoodOrder.OrderStatus.PENDING, customer, restaurant,
                LocalDateTime.now());

        // build the set of OrderItems
        Set<OrderItem> orderItems = cartItems.stream()
                                             .map(cartItem -> new OrderItem(cartItem.getQuantity(), foodOrder, cartItem.getFood()))
                                             .collect(Collectors.toSet());

        // add OrderItems to the Order
        foodOrder.setOrderItems(orderItems);

        // save new order
        FoodOrder savedFoodOrder = foodOrderRepository.save(foodOrder);

        logger.info(String.format("UPDATE - placed new order nr %d\n", savedFoodOrder.getId()));

        // delete cartItems
        cartItemRepository.deleteAlllByCustomer(customer);

        logger.info(String.format("UPDATE - set the cart of user %s to empty",
                customer.getUserName()));

        return savedFoodOrder;
    }

    /**
     * Returns the list of orders in the decreasing order of the dates of the active user.
     * @return the order list.
     * @throws AccessRestrictedToCustomersException if the active user is not a customer.
     */
    public List<CustomerOrderDTO> getActiveCustomersOrderHistory() throws AccessRestrictedToCustomersException {
        // find the user
        Customer customer = authenticationService.getCurrentCustomer();

        return customer
                .getOrders()
                .stream()
                .sorted(Comparator.comparing(FoodOrder::getDateTime).reversed())
                .map(order -> new CustomerOrderDTO(
                        order.getRestaurant().getName(),
                        order.getOrderStatus().toString(),
                        order.getDateTime(),
                        order.getOrderItems().stream()
                                .map(orderItem ->
                                        new AbstractMap.SimpleEntry<>(
                                                mapper.map(orderItem.getFood(), FoodDTO.class),
                                                orderItem.getQuantity()
                                        ))
                                .collect(Collectors.toMap(
                                        AbstractMap.SimpleEntry::getKey,
                                        AbstractMap.SimpleEntry::getValue))))
                .collect(Collectors.toList());
    }
}
