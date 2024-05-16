package com.raghad.sales_management_system.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.context.ApplicationContext;

import com.raghad.sales_management_system.services.ProductReportService;
import com.raghad.sales_management_system.DTOs.ProductReport;

@RestController
@RequestMapping(path = "api/product-report")
public class ProductReportController {
    private final ApplicationContext context;
    private ProductReportService productReportService;

    public ProductReportController(ApplicationContext context) {
        this.context = context;
    }

    @GetMapping
    public ProductReport generateProductReport() {
        this.productReportService = this.context.getBean(ProductReportService.class);
        return this.productReportService.generateProductReport();
    }
}
