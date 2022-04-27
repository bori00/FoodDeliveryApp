package com.example.food_delivery.controller;

import com.example.food_delivery.service.menu_pdf_generation.MenuPDFGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuAccessController {

    @Autowired
    MenuPDFGenerationService menuPDFGenerationService;

    @GetMapping("/get_my_menu_in_pdf")
    @PreAuthorize("hasAuthority('RESTAURANT_ADMIN')")
    public InputStreamResource getMyRestaurantsMenuInPdf() {
        menuPDFGenerationService.createMenuPDF();
        return null;
    }
}
