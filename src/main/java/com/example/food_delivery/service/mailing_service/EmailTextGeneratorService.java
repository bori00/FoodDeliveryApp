package com.example.food_delivery.service.mailing_service;

import com.example.food_delivery.model.FoodOrder;
import com.example.food_delivery.model.OrderItem;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Service
public class EmailTextGeneratorService {

    public static String ORDER_EMAIL_TEMPLATE = "Dear %s,\n\n" +
        "As the Admin of the %s, the Food Delivery App informs you that a new order just arrived" +
            ".\n\n" +
        " - Custumer: %s\n" +
        " - Date: %s\n" +
        " - Total Price: %.2f$\n" +
        " - Items: \n%s\n\n" +
        "Best wishes, \n" +
        "The Food Delivery Team";

    public static String ORDER_ITEM_TEMPLATE = "--> %s .................... %d x %.2f$";

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public String generateOrderEmailReport(FoodOrder foodOrder) {
        return String.format(ORDER_EMAIL_TEMPLATE,
                foodOrder.getRestaurant().getAdmin().getUserName(),
                foodOrder.getRestaurant().getName(),
                foodOrder.getCustomer().getUserName(),
                dateFormatter.format(foodOrder.getDateTime()),
                foodOrder.getTotalPrice(),
                getOrderItemsListReport(foodOrder));
    }

    public String getOrderItemsListReport(FoodOrder foodOrder) {
        return foodOrder.getOrderItems().stream().map(this::getOrderItemReport).collect(Collectors.joining("\n"));
    }

    public String getOrderItemReport(OrderItem orderItem) {
        return String.format(ORDER_ITEM_TEMPLATE, orderItem.getFood().getName(),
                orderItem.getQuantity(), orderItem.getFood().getPrice());
    }
}
