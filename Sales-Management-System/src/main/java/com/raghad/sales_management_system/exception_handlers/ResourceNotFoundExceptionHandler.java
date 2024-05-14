package com.raghad.sales_management_system.exception_handlers;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;

import com.raghad.sales_management_system.exceptions.ResourceNotFoundException;
import com.raghad.sales_management_system.DTOs.ErrorDetail;
import com.raghad.sales_management_system.DTOs.ErrorResponse;

@RestControllerAdvice
public class ResourceNotFoundExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundResourceException(ResourceNotFoundException ex) {
        String description = ex.getMessage();
        List<ErrorDetail> errorDetails = this.getErrorDetails();

        var errorResponse = new ErrorResponse(description, errorDetails);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    private List<ErrorDetail> getErrorDetails() {
        return List.of(new ErrorDetail("id",
                "The resource id must exist"));
    }
}
