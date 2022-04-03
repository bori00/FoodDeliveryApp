package com.example.food_delivery.controller;

import com.example.food_delivery.model.DTO.DeliveryZoneDTO;
import com.example.food_delivery.model.DeliveryZone;
import com.example.food_delivery.model.FoodOrder;
import com.example.food_delivery.service.util.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("util")
public class UtilController {
    @Autowired
    private UtilService utilService;

    @GetMapping("/get_all_delivery_zones")
    public List<DeliveryZoneDTO> getAllDeliveryZones() {
        return utilService.getAllDeliveryZones();
    }

    @GetMapping("/get_all_food_categories")
    public List<String> getAllFoodCategories() {
        return utilService.getAllFoodCategories();
    }

    @GetMapping("/get_all_possible_next_order_statuses")
    public List<String> getAllPossibleNextOrderStatuses(@RequestParam FoodOrder.OrderStatus currentStatus) {
        return utilService.getAllPossibleNextOrderStatuses(currentStatus);
    }

    @GetMapping("/get_all_possible_order_statuses")
    public List<String> getAllPossibleOrderStatuses() {
        return utilService.getAllPossibleOrderStatuses();
    }
}
