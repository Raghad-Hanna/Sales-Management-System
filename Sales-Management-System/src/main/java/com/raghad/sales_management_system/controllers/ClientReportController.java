package com.raghad.sales_management_system.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.context.ApplicationContext;

import com.raghad.sales_management_system.services.ClientReportService;
import com.raghad.sales_management_system.DTOs.ClientReport;

@RestController
@RequestMapping(path = "api/client-report")
public class ClientReportController {
    private final ApplicationContext context;
    private ClientReportService clientReportService;

    public ClientReportController(ApplicationContext context) {
        this.context = context;
    }

    @GetMapping
    public ClientReport generateClientReport() {
        this.clientReportService = this.context.getBean(ClientReportService.class);
        return this.clientReportService.generateClientReport();
    }
}
