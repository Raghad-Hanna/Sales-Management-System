package com.raghad.sales_management_system.exception_handlers;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;

import com.raghad.sales_management_system.exceptions.NotAvailableProductQuantityException;
import com.raghad.sales_management_system.DTOs.ErrorDetail;
import com.raghad.sales_management_system.DTOs.ErrorResponse;

@RestControllerAdvice
public class NotAvailableProductQuantityExceptionHandler {
    @ExceptionHandler(NotAvailableProductQuantityException.class)
    public ResponseEntity<ErrorResponse> handleNotAvailableProductQuantityException(NotAvailableProductQuantityException ex) {
        String description = ex.getMessage();
        List<ErrorDetail> errorDetails = this.getErrorDetails(ex.getProductId());

        var errorResponse = new ErrorResponse(description, errorDetails);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    private List<ErrorDetail> getErrorDetails(Integer productId) {
        return List.of(new ErrorDetail("quantity",
                "The quantity of the the product with an id of "
                        + productId + " is less than what is required"));
    }
}
