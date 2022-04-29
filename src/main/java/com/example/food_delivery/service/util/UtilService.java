package com.example.food_delivery.service.util;

import com.example.food_delivery.model.DTO.DeliveryZoneDTO;
import com.example.food_delivery.model.DeliveryZone;
import com.example.food_delivery.model.FoodCategory;
import com.example.food_delivery.model.FoodOrder;
import com.example.food_delivery.repository.DeliveryZoneRepository;
import com.example.food_delivery.repository.FoodCategoryRepository;
import com.example.food_delivery.service.admin_order_management.order_states.OrderStateFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service providing utilities for other services.
 */
@Service
public class UtilService {

    @Autowired
    private DeliveryZoneRepository deliveryZoneRepository;

    @Autowired
    private FoodCategoryRepository foodCategoryRepository;

    @Autowired
    private ModelMapper mapper;

    /**
     * @return the list of all existing delivery zones.
     */
    public List<DeliveryZoneDTO> getAllDeliveryZones() {
        return deliveryZoneRepository.findAll().stream().map(zone -> mapper.map(zone,
                DeliveryZoneDTO.class)).collect(Collectors.toList());
    }

    /**
     * @return the list of all existing food categories.
     */
    public List<String> getAllFoodCategories() {
        return foodCategoryRepository.findAll().stream().map(FoodCategory::getName).collect(Collectors.toList());
    }

    /**
     * Returns all OrderStatuses which can be reached from the current orderStatus.
     * @param orderStatus is the starting state of the transition.
     * @return the possible ending states of the transition.
     */
    public List<String> getAllPossibleNextOrderStatuses(FoodOrder.OrderStatus orderStatus) {
        return OrderStateFactory.createOrderState(orderStatus).getAllowedTransitions().stream().map(Enum::toString).collect(Collectors.toList());
    }

    /**
     * @return the list of all existing order statuses.
     */
    public List<String> getAllPossibleOrderStatuses() {
        return Arrays.stream(FoodOrder.OrderStatus.values()).map(Enum::toString).collect(Collectors.toList());
    }
}
