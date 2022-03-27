package com.example.food_delivery.service.admin_order_management;

import com.example.food_delivery.model.DTO.AdminOrderDTO;
import com.example.food_delivery.model.DTO.FoodDTO;
import com.example.food_delivery.model.FoodOrder;
import com.example.food_delivery.model.RestaurantAdmin;
import com.example.food_delivery.repository.FoodOrderRepository;
import com.example.food_delivery.repository.RestaurantRepository;
import com.example.food_delivery.service.authentication.AuthenticationService;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToAdminsException;
import com.example.food_delivery.service.restaurant_management.exceptions.MoreThanOneRestaurantPerAdminException;
import com.example.food_delivery.service.restaurant_management.exceptions.NoRestaurantSetupForAdminException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminOrderService {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private FoodOrderRepository foodOrderRepository;

    @Autowired
    private ModelMapper mapper;

    public List<AdminOrderDTO> getFilteredRestaurantsOrders(Collection<FoodOrder.OrderStatus> statuses) throws AccessRestrictedToAdminsException, NoRestaurantSetupForAdminException {
        // get active user
        RestaurantAdmin admin = authenticationService.getCurrentAdmin();

        // verify that the user does have a restaurant
        if (admin.getRestaurant() == null) {
            throw new NoRestaurantSetupForAdminException();
        }

        return foodOrderRepository.findAllByOrderStatusInAndRestaurant(statuses, admin.getRestaurant())
                .stream()
                .map(order -> new AdminOrderDTO(
                        order.getId(),
                        order.getCustomer().getUserName(),
                        order.getOrderStatus().toString(),
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
