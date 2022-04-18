package com.example.food_delivery.service.authentication.exceptions;

/**
 * Exception thrown when a user with no customer rights attempts to perform an operation restricted
 * to customers only.
 */
public class AccessRestrictedToCustomersException  extends Exception {
}
