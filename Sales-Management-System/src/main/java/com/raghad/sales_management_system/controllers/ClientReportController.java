package com.raghad.sales_management_system.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.raghad.sales_management_system.services.ClientReportService;
import com.raghad.sales_management_system.DTOs.ClientReport;

@RestController
@RequestMapping(path = "api/client-report")
public class ClientReportController {
    private final ClientReportService clientReportService;

    public ClientReportController(ClientReportService clientReportService) {
        this.clientReportService = clientReportService;
    }

    @GetMapping
    public ClientReport generateClientReport() {
        return this.clientReportService.generateClientReport();
    }
}
