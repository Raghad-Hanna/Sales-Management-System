package com.raghad.sales_management_system.exception_handlers;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.raghad.sales_management_system.exceptions.ResourceIdsMismatchException;

@RestControllerAdvice
public class ResourceIdsMismatchExceptionHandler {
    @ExceptionHandler(ResourceIdsMismatchException.class)
    public ResponseEntity<String> handleResourceIdsMismatchExceptionHandler(ResourceIdsMismatchException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
