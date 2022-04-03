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

@Service
public class UtilService {

    @Autowired
    private DeliveryZoneRepository deliveryZoneRepository;

    @Autowired
    private FoodCategoryRepository foodCategoryRepository;

    @Autowired
    private ModelMapper mapper;

    public List<DeliveryZoneDTO> getAllDeliveryZones() {
        return deliveryZoneRepository.findAll().stream().map(zone -> mapper.map(zone,
                DeliveryZoneDTO.class)).collect(Collectors.toList());
    }

    public List<String> getAllFoodCategories() {
        return foodCategoryRepository.findAll().stream().map(FoodCategory::getName).collect(Collectors.toList());
    }

    public List<String> getAllPossibleNextOrderStatuses(FoodOrder.OrderStatus orderStatus) {
        return OrderStateFactory.createOrderState(orderStatus).getAllowedTransitions().stream().map(Enum::toString).collect(Collectors.toList());
    }

    public List<String> getAllPossibleOrderStatuses() {
        return Arrays.stream(FoodOrder.OrderStatus.values()).map(Enum::toString).collect(Collectors.toList());
    }
}
