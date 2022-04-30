package com.example.food_delivery.service.admin_order_management;

import com.example.food_delivery.FoodDeliveryApplication;
import com.example.food_delivery.model.*;
import com.example.food_delivery.model.DTO.AdminOrderDTO;
import com.example.food_delivery.repository.FoodOrderRepository;
import com.example.food_delivery.service.authentication.AuthenticationService;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToAdminsException;
import com.example.food_delivery.service.restaurant_management.exceptions.NoRestaurantSetupForAdminException;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FoodDeliveryApplication.class)
class AdminOrderServiceTest {

    @Autowired
    private AdminOrderService adminOrderService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private FoodOrderRepository foodOrderRepository;

    private static final RestaurantAdmin ADMIN = new RestaurantAdmin("adminName", "adminPass",
            "adminEmail");
    private static final Customer CUSTOMER1 = new Customer("custName1", "custPass1",
            "custEmail1");
    private static final Customer CUSTOMER2 = new Customer("custName2", "custPass2",
            "custEmail2");
    private static final Restaurant RESTAURANT = new Restaurant("RestName", "RestAddress", ADMIN,
            Set.of(new DeliveryZone(1L, "Zone1"), new DeliveryZone(2L, "Zone2")));

    private static final FoodOrder ORDER1 = new FoodOrder(FoodOrder.OrderStatus.PENDING,
            CUSTOMER1, RESTAURANT, LocalDateTime.of(2022, 3, 15, 12, 30, 0));
    private static final FoodOrder ORDER2 = new FoodOrder(FoodOrder.OrderStatus.IN_DELIVERY,
            CUSTOMER1, RESTAURANT, LocalDateTime.of(2022, 3, 16, 12, 30, 0));
    private static final FoodOrder ORDER3 = new FoodOrder(FoodOrder.OrderStatus.IN_DELIVERY,
            CUSTOMER2, RESTAURANT, LocalDateTime.of(2022, 3, 17, 12, 30, 0));
    private static final FoodOrder ORDER4 = new FoodOrder(FoodOrder.OrderStatus.IN_DELIVERY,
            CUSTOMER2, RESTAURANT, LocalDateTime.of(2022, 3, 17, 12, 40, 0));
    private static final FoodOrder ORDER5 = new FoodOrder(FoodOrder.OrderStatus.ACCEPTED,
            CUSTOMER1, RESTAURANT, LocalDateTime.of(2022, 3, 18, 12, 30, 0));

    private static final AdminOrderDTO orderDTO1 = new AdminOrderDTO(null,
            CUSTOMER1.getUserName(), FoodOrder.OrderStatus.PENDING.toString(),
            LocalDateTime.of(2022, 3, 15, 12, 30, 0), new HashMap<>());
    private static final AdminOrderDTO orderDTO2 = new AdminOrderDTO(null,
            CUSTOMER1.getUserName(), FoodOrder.OrderStatus.IN_DELIVERY.toString(),
            LocalDateTime.of(2022, 3, 16, 12, 30, 0), new HashMap<>());
    private static final AdminOrderDTO orderDTO3 = new AdminOrderDTO(null,
            CUSTOMER2.getUserName(), FoodOrder.OrderStatus.IN_DELIVERY.toString(),
            LocalDateTime.of(2022, 3, 17, 12, 30, 0), new HashMap<>());
    private static final AdminOrderDTO orderDTO4 = new AdminOrderDTO(null,
            CUSTOMER2.getUserName(), FoodOrder.OrderStatus.IN_DELIVERY.toString(),
            LocalDateTime.of(2022, 3, 17, 12, 40, 0), new HashMap<>());
    private static final AdminOrderDTO orderDTO5 = new AdminOrderDTO(null,
            CUSTOMER1.getUserName(), FoodOrder.OrderStatus.ACCEPTED.toString(),
            LocalDateTime.of(2022, 3, 18, 12, 30, 0), new HashMap<>());

    @BeforeEach
    void setUp() throws AccessRestrictedToAdminsException {
        ADMIN.setRestaurant(RESTAURANT);

        Mockito.when(authenticationService.getCurrentAdmin()).thenReturn(
                ADMIN);

        Mockito.when(foodOrderRepository.findAllByOrderStatusInAndRestaurantOrderByDateTimeDesc(Set.of(FoodOrder.OrderStatus.IN_DELIVERY), RESTAURANT)).thenReturn(
                List.of(ORDER2, ORDER3, ORDER4));

        Mockito.when(foodOrderRepository.findAllByOrderStatusInAndRestaurantOrderByDateTimeDesc(new HashSet<>(Arrays.asList(FoodOrder.OrderStatus.values()))
                , RESTAURANT)).thenReturn(
                List.of(ORDER1, ORDER2, ORDER3, ORDER4, ORDER5));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getFilteredSortedRestaurantsOrders_restrictedSetOfStatuses() throws AccessRestrictedToAdminsException, NoRestaurantSetupForAdminException {

        List<AdminOrderDTO> orders =
                adminOrderService.getFilteredSortedRestaurantsOrders(Set.of(FoodOrder.OrderStatus.IN_DELIVERY));

        Assertions.assertIterableEquals(List.of(orderDTO2, orderDTO3, orderDTO4), orders);

    }

    @Test
    void updateOrderState() {
    }
}