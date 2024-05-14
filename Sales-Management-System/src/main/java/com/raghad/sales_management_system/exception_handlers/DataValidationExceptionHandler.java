package com.raghad.sales_management_system.exception_handlers;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.ArrayList;

import com.raghad.sales_management_system.DTOs.ErrorDetail;
import com.raghad.sales_management_system.DTOs.ErrorResponse;

@RestControllerAdvice
public class DataValidationExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleDataValidationException(MethodArgumentNotValidException ex) {
        String description = "Invalid data. Correct the errors.";
        List<ErrorDetail> errorDetails = this.extractErrorDetails(ex.getBindingResult());

        var errorResponse = new ErrorResponse(description, errorDetails);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    private List<ErrorDetail> extractErrorDetails(BindingResult bindingResult) {
        List<ErrorDetail> errorDetails = new ArrayList<>();
        for(FieldError fieldError: bindingResult.getFieldErrors()) {
            errorDetails.add(new ErrorDetail(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return errorDetails;
    }
}
