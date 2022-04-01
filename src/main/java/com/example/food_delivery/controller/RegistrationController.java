package com.example.food_delivery.controller;

import com.example.food_delivery.model.DTO.UserDTO;
import com.example.food_delivery.service.authentication.RegistrationService;
import com.example.food_delivery.service.authentication.exceptions.DuplicateUsernameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    void register(@Valid @RequestBody UserDTO userDTO) throws DuplicateUsernameException {
        registrationService.register(userDTO);
    }

}
