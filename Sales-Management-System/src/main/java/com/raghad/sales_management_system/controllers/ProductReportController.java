package com.raghad.sales_management_system.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.raghad.sales_management_system.services.ProductReportService;
import com.raghad.sales_management_system.DTOs.ProductReport;

@RestController
@RequestMapping(path = "api/product-report")
public class ProductReportController {
    private final ProductReportService productReportService;

    public ProductReportController(ProductReportService productReportService) {
        this.productReportService = productReportService;
    }

    @GetMapping
    public ProductReport generateProductReport() {
        return this.productReportService.generateProductReport();
    }
}
