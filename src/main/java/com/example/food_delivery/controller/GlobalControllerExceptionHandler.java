package com.example.food_delivery.controller;

import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToAdminsException;
import com.example.food_delivery.service.authentication.exceptions.AuthenticationRequiredException;
import com.example.food_delivery.service.authentication.exceptions.DuplicateUsernameException;
import com.example.food_delivery.service.food_browsing.RestaurantNotFoundException;
import com.example.food_delivery.service.restaurant_management.exceptions.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @Getter
    @AllArgsConstructor
    public static class ExceptionResponse {
        private final List<String> messages;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ExceptionResponse handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        return new ExceptionResponse(
                ex.getBindingResult()
                        .getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.toList()));
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)  // 403
    @ExceptionHandler(DuplicateUsernameException.class)
    public @ResponseBody
    ExceptionResponse handleDuplicateUsernameException(
            Exception ex) {
        return new ExceptionResponse(List.of("This username is already taken. Please specify " +
                "another one!"));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)  // 401
    @ExceptionHandler(AuthenticationRequiredException.class)
    public @ResponseBody
    ExceptionResponse handleAuthenticationRequiredException(
            Exception ex) {
        return new ExceptionResponse(List.of("You must be authenticated to access this " +
                "functionality!"));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)  // 401
    @ExceptionHandler(AccessRestrictedToAdminsException.class)
    public @ResponseBody
    ExceptionResponse handleAccessRestrictedToAdminsException(
            Exception ex) {
        return new ExceptionResponse(List.of("You must be a Restaurant Admin to acess this " +
                "functionality!"));
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)  // 403
    @ExceptionHandler(MissingAvailableDeliveryZoneException.class)
    public @ResponseBody
    ExceptionResponse handleMissingAvailableDeliveryZoneException(
            Exception ex) {
        return new ExceptionResponse(List.of("Each restaurant must have at least one valid " +
                "available delivery zone!"));
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)  // 403
    @ExceptionHandler(DuplicateRestaurantNameException.class)
    public @ResponseBody
    ExceptionResponse handleDuplicateRestaurantNameException(
            Exception ex) {
        return new ExceptionResponse(List.of("This restaurant name is already taken. Please " +
                "choose another one!"));
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)  // 403
    @ExceptionHandler(MoreThanOneRestaurantPerAdminException.class)
    public @ResponseBody
    ExceptionResponse handleMoreThanOneRestaurantPerAdminException(
            Exception ex) {
        return new ExceptionResponse(List.of("You can't have a second restaurant for the same " +
                "account."));
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)  // 403
    @ExceptionHandler(InvalidFoodCategoryException.class)
    public @ResponseBody
    ExceptionResponse handleInvalidFoodCategoryException(
            Exception ex) {
        return new ExceptionResponse(List.of("The requested food category doesn't exist."));
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)  // 403
    @ExceptionHandler(DuplicateFoodNameInsideRestaurantException.class)
    public @ResponseBody
    ExceptionResponse handleDuplicateFoodNameInsideRestaurantException(
            Exception ex) {
        return new ExceptionResponse(List.of("There is another menu item with the same name in " +
                "your restaurant. Please choose another name."));
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)  // 403
    @ExceptionHandler(NoRestaurantSetupForAdminException.class)
    public @ResponseBody
    ExceptionResponse handleMissingRestaurantForAdminException(
            Exception ex) {
        return new ExceptionResponse(List.of("You must first set up your restaurant before " +
                "accessing this functionality."));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RestaurantNotFoundException.class)
    public @ResponseBody
    ExceptionResponse handleRestaurantNotFoundException(
            Exception ex) {
        return new ExceptionResponse(List.of("The restaurant with the requested name was not " +
                "found."));
    }
}
