package com.example.food_delivery.service.authentication.exceptions;

/**
 * Exception thrown when a user with no admin rights attempts to perform an operation restricted
 * to admins only.
 */
public class AccessRestrictedToAdminsException extends Exception {
}
