package com.example.food_delivery.service.restaurant_management.exceptions;

/**
 * Exception thrown when an admin attempts to perform an action on their owned restaurant, but
 * theu do not own a restaurant yet.
 */
public class NoRestaurantSetupForAdminException extends Throwable {
}
