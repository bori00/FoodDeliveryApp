package com.example.food_delivery.service.customer_order_management;

import com.example.food_delivery.model.*;
import com.example.food_delivery.model.DTO.CustomerOrderDTO;
import com.example.food_delivery.model.DTO.FoodDTO;
import com.example.food_delivery.repository.CartItemRepository;
import com.example.food_delivery.repository.FoodOrderRepository;
import com.example.food_delivery.service.authentication.AuthenticationService;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToAdminsException;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToCustomersException;
import com.example.food_delivery.service.cart.exceptions.CartItemsFromMultipleRestaurantsException;
import com.example.food_delivery.service.customer_order_management.exceptions.EmptyOrderException;
import org.modelmapper.ModelMapper;
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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void placeOrder() throws EmptyOrderException, CartItemsFromMultipleRestaurantsException, AccessRestrictedToCustomersException {
        // find the user who places the order
        Customer customer = authenticationService.getCurrentCustomer();

        // find the items to be ordered
        Set<CartItem> cartItems = customer.getCartItems();

        // ensure the order is non-empty
        if (cartItems.isEmpty()) {
            throw new EmptyOrderException();
        }

        // find the restaurant to which the order refers
        Set<Restaurant> itemRestaurants = cartItems.stream()
                                                    .map(cartItem -> cartItem.getFood().getRestaurant())
                                                    .collect(Collectors.toSet());

        // ensure all items are from the same restaurant
        if (itemRestaurants.size() > 1) {
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
        foodOrderRepository.save(foodOrder);

        // delete cartItems
        cartItemRepository.deleteAlllByCustomer(customer);
    }

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
