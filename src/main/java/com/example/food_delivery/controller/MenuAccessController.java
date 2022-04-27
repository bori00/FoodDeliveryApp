package com.example.food_delivery.controller;

import com.example.food_delivery.model.DTO.FoodDTO;
import com.example.food_delivery.model.Food;
import com.example.food_delivery.model.FoodCategory;
import com.example.food_delivery.service.menu_pdf_generation.MenuPDFGenerationService;
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

@RestController
public class MenuAccessController {

    @Autowired
    MenuPDFGenerationService menuPDFGenerationService;

    @GetMapping(value="/get_my_menu_in_pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @PreAuthorize("hasAuthority('RESTAURANT_ADMIN')")
    public ResponseEntity<InputStreamResource> getMyRestaurantsMenuInPdf() {
        Map<FoodCategory, List<FoodDTO>> map = new HashMap<>();

        FoodCategory appetizer = new FoodCategory(1L, "appetizer");
        map.put(appetizer, List.of(new FoodDTO("Food1", 10.0, null, "appetizer"),
                new FoodDTO("Food2", 30.0, "d2", "appetizer")));

        FoodCategory main = new FoodCategory(1L, "main course");
        map.put(main, List.of(new FoodDTO("Food1", 10.0, "", "appetizer"),
                new FoodDTO("Food2", 30.0, "d2", "appetizer")));

        ByteArrayInputStream menuPDF = menuPDFGenerationService.createMenuPDF("Little Italy", map);

        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(menuPDF));
    }
}
