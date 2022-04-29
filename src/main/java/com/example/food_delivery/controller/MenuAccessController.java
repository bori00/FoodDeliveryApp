package com.example.food_delivery.controller;

import com.example.food_delivery.model.DTO.FoodDTO;
import com.example.food_delivery.model.Food;
import com.example.food_delivery.model.FoodCategory;
import com.example.food_delivery.service.authentication.exceptions.AccessRestrictedToAdminsException;
import com.example.food_delivery.service.mailing_service.MailingService;
import com.example.food_delivery.service.menu_pdf_generation.MenuPDFGenerationService;
import com.example.food_delivery.service.restaurant_management.RestaurantManagementService;
import com.example.food_delivery.service.restaurant_management.exceptions.NoRestaurantSetupForAdminException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@RestController
public class MenuAccessController {

    @Autowired
    MenuPDFGenerationService menuPDFGenerationService;

    @Autowired
    RestaurantManagementService restaurantManagementService;

    private static final Logger logger = LoggerFactory.getLogger(MenuAccessController.class);

    @GetMapping(value="/get_my_menu_in_pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @PreAuthorize("hasAuthority('RESTAURANT_ADMIN')")
    public ResponseEntity<InputStreamResource> getMyRestaurantsMenuInPdf() throws AccessRestrictedToAdminsException, NoRestaurantSetupForAdminException {
        logger.info("REQUEST - /get_my_menu_in_pdf");

        List<FoodDTO> foodsList = restaurantManagementService.getActiveAdminsRestaurantsMenu(null);

        String restaurantName = restaurantManagementService.getActiveAdminsRestaurantsName();

        Map<String, List<FoodDTO>> foodMap =
                foodsList.stream().collect(groupingBy(FoodDTO::getFoodCategory));

        ByteArrayInputStream menuPDF = menuPDFGenerationService.createMenuPDF(restaurantName,
                foodMap);

        logger.info("EVENT - generated PDF for menu");

        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(menuPDF));
    }
}
