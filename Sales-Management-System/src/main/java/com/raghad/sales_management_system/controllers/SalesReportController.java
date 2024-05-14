package com.raghad.sales_management_system.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.raghad.sales_management_system.services.SalesReportService;
import com.raghad.sales_management_system.DTOs.SalesReport;

@RestController
@RequestMapping(path = "api/sales-report")
public class SalesReportController {
    private final SalesReportService salesReportService;

    public SalesReportController(SalesReportService salesReportService) {
        this.salesReportService = salesReportService;
    }

    @GetMapping
    public SalesReport generateSalesReportInDateRange(@RequestParam("start-date") String startDate,
                                                      @RequestParam("end-date") String endDate) {
        return this.salesReportService.generateSalesReport(startDate, endDate);
    }
}
