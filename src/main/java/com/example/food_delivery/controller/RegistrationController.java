package com.example.food_delivery.controller;

import com.example.food_delivery.model.DTO.UserDTO;
import com.example.food_delivery.service.authentication.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.*;
import java.util.Set;

@RestController
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/register")
    ResponseEntity<String> register(@Valid @RequestBody UserDTO userDTO) {
        System.out.println(userDTO);
        registrationService.register(userDTO);
        return ResponseEntity.ok("Registration Successful");
    }

}
