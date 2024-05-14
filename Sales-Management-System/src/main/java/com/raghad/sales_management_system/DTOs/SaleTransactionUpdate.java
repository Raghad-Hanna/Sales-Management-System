package com.raghad.sales_management_system.DTOs;

import jakarta.validation.constraints.Positive;

import com.raghad.sales_management_system.entities.SaleTransaction;

public record SaleTransactionUpdate(Integer id,
                                    @Positive(message = "The quantity must be positive") int quantity) {
    public SaleTransaction toSaleTransaction() {
        return new SaleTransaction(this.id, this.quantity);
    }
}
