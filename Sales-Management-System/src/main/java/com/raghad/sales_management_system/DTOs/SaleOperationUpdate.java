package com.raghad.sales_management_system.DTOs;

import java.util.List;
import java.util.ArrayList;

import com.raghad.sales_management_system.entities.SaleOperation;
import com.raghad.sales_management_system.entities.SaleTransaction;

public record SaleOperationUpdate(Integer id,
                                  List<SaleTransactionUpdate> saleTransactionUpdates) {
    public SaleOperation toSaleOperation() {
        List<SaleTransaction> saleTransactions = new ArrayList<>();
        for(var saleTransactionUpdate: this.saleTransactionUpdates) {
            saleTransactions.add(saleTransactionUpdate.toSaleTransaction());
        }
        return new SaleOperation(this.id, saleTransactions);
    }
}
