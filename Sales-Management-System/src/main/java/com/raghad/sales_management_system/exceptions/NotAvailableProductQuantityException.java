package com.raghad.sales_management_system.exceptions;

public class NotAvailableProductQuantityException extends RuntimeException {
    private Integer productId;

    public NotAvailableProductQuantityException(String message, Integer productId) {
        super(message);
        this.productId = productId;
    }

    public Integer getProductId() {
        return this.productId;
    }
}
