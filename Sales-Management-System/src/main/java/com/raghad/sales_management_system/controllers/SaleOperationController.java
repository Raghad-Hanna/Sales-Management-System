package com.raghad.sales_management_system.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import java.util.List;

import com.raghad.sales_management_system.services.SaleOperationService;
import com.raghad.sales_management_system.entities.SaleOperation;
import com.raghad.sales_management_system.DTOs.SaleOperationUpdate;

@RestController
@RequestMapping(path = "api/sale-operations")
public class SaleOperationController {
    private final SaleOperationService saleOperationService;

    public SaleOperationController(SaleOperationService saleOperationService) {
        this.saleOperationService = saleOperationService;
    }

    @GetMapping
    public List<SaleOperation> getSaleOperations() {
        return this.saleOperationService.getSaleOperations();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SaleOperation createSaleOperation(@Valid @RequestBody SaleOperation saleOperation) {
        return this.saleOperationService.createSaleOperation(saleOperation);
    }

    @PutMapping(path = "/{id}")
    public SaleOperation updateSaleOperation(@Valid @RequestBody SaleOperationUpdate saleOperationUpdate, @PathVariable("id") Integer id) {
        return this.saleOperationService.updateSaleOperation(saleOperationUpdate.toSaleOperation(), id);
    }
}
