package com.example.food_delivery.service.cart;

import com.example.food_delivery.FoodDeliveryApplication;
import com.example.food_delivery.model.*;
import com.example.food_delivery.model.DTO.CartItemDTO;
import com.example.food_delivery.model.DTO.CustomerCartDTO;
import com.example.food_delivery.model.DTO.FoodDTO;
import com.example.food_delivery.repository.CartItemRepository;
import com.example.food_delivery.repository.FoodRepository;
import com.example.food_delivery.repository.RestaurantRepository;
import com.example.food_delivery.service.authentication.AuthenticationService;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToAdminsException;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToCustomersException;
import com.example.food_delivery.service.cart.exceptions.CartItemsFromMultipleRestaurantsException;
import com.example.food_delivery.service.cart.exceptions.FoodNotFoundException;
import com.example.food_delivery.service.food_browsing.exceptions.RestaurantNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FoodDeliveryApplication.class)
class CartServiceTest {

    @MockBean
    private AuthenticationService authenticationService;

    @Autowired
    private CartService cartService;

    @MockBean
    private CartItemRepository cartItemRepository;

    @MockBean
    private RestaurantRepository restaurantRepository;

    @MockBean
    private FoodRepository foodRepository;

    private static final RestaurantAdmin ADMIN = new RestaurantAdmin("adminName", "adminPass",
            "adminEmail");
    private static final Customer CUSTOMER1 = new Customer("custName1", "custPass1",
            "custEmail1");
    private static final Restaurant RESTAURANT1 = new Restaurant("RestName", "RestAddress", ADMIN,
            Set.of(new DeliveryZone(1L, "Zone1"), new DeliveryZone(2L, "Zone2")));
    private static final Restaurant RESTAURANT2 = new Restaurant("RestName2", "RestAddress2", ADMIN,
            Set.of(new DeliveryZone(1L, "Zone1"), new DeliveryZone(2L, "Zone2")));
    private static final Food FOOD1 = new Food("pie", 12.0, "blabla", new FoodCategory(1L,
            "desert"),
            RESTAURANT1);
    private static final Food FOOD2 = new Food("wine", 15.0, "blabla2", new FoodCategory(1L,
            "desert"), RESTAURANT2);
    private static final Food FOOD3 = new Food("pizza", 18.0, "blabla3", new FoodCategory(2L,
            "main"),
            RESTAURANT1);

    private static final CartItem oldCartItem1 = new CartItem(1, CUSTOMER1, FOOD1);

    private static final CartItemDTO newCartItemDTO1 = new CartItemDTO("pie", 4, "RestName");
    private static final CartItemDTO newCartItemDTO2 = new CartItemDTO("pizza", 3, "RestName");
    private static final CartItemDTO newCartItemDTO3 = new CartItemDTO("wine", 1, "RestName2");
    private static final CartItemDTO newCartItemDTO4 = new CartItemDTO("wine", 1, "RestName2");

    private static final CustomerCartDTO customer1Cart = new CustomerCartDTO("RestName",
            new HashMap<>());

    @BeforeEach
    void setUp() throws AccessRestrictedToAdminsException, AccessRestrictedToCustomersException {
        ADMIN.setRestaurant(RESTAURANT1);

        FOOD1.setRestaurant(RESTAURANT1);
        FOOD2.setRestaurant(RESTAURANT2);
        FOOD3.setRestaurant(RESTAURANT1);

        CUSTOMER1.setCartItems(Set.of(oldCartItem1));

        Mockito.when(authenticationService.getCurrentCustomer()).thenReturn(CUSTOMER1);

        Mockito.when(restaurantRepository.findByName("RestName")).thenReturn(java.util.Optional.of(RESTAURANT1));

        Mockito.when(restaurantRepository.findByName("RestName2")).thenReturn(java.util.Optional.of(RESTAURANT2));

        Mockito.when(foodRepository.findByNameAndRestaurant("pie", RESTAURANT1)).thenReturn(java.util.Optional.of(FOOD1));

        Mockito.when(foodRepository.findByNameAndRestaurant("pizza", RESTAURANT1)).thenReturn(java.util.Optional.of(FOOD3));

        Mockito.when(foodRepository.findByNameAndRestaurant("wine", RESTAURANT2)).thenReturn(java.util.Optional.of(FOOD2));

        Mockito.when(cartItemRepository.findByCustomerAndFood(CUSTOMER1, FOOD1)).thenReturn(Optional.of(oldCartItem1));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addItemToCart_itemAlreadyInCart() throws AccessRestrictedToCustomersException,
            CartItemsFromMultipleRestaurantsException, FoodNotFoundException, RestaurantNotFoundException {
        cartService.addItemToCart(newCartItemDTO1);
        assertEquals(4, oldCartItem1.getQuantity());

    }

    @Test
    void addItemToCart_newItemInCart() throws AccessRestrictedToCustomersException,
            CartItemsFromMultipleRestaurantsException, FoodNotFoundException, RestaurantNotFoundException {
        cartService.addItemToCart(newCartItemDTO2);
    }

    @Test
    void addItemToCart_itemsFromMultipleRestaurantsException() {
        assertThrows(CartItemsFromMultipleRestaurantsException.class, () -> {
            cartService.addItemToCart(newCartItemDTO4);
        });
    }

    @Test
    void getCustomersCartContent() throws AccessRestrictedToCustomersException, CartItemsFromMultipleRestaurantsException, FoodNotFoundException, RestaurantNotFoundException {
        cartService.addItemToCart(newCartItemDTO1);

        customer1Cart.getSelectedItemsToQuantity().put(new FoodDTO("pie", 12.0, "blabla", "desert"), 4);

        CustomerCartDTO cartDTO = cartService.getCustomersCartContent();

        assertThat(cartDTO).usingRecursiveComparison().isEqualTo(customer1Cart);

        customer1Cart.getSelectedItemsToQuantity().put(new FoodDTO("pie", 12.0, "blabla", "desert"), 3);

        assertThat(cartDTO).usingRecursiveComparison().isNotEqualTo(customer1Cart);
    }

    @Test
    void getCartItemQuantity() throws AccessRestrictedToCustomersException, CartItemsFromMultipleRestaurantsException, FoodNotFoundException, RestaurantNotFoundException {
        cartService.addItemToCart(newCartItemDTO1);

        int quantity = cartService.getCartItemQuantity("pie", "RestName");

        assertEquals(4, quantity);

    }
}