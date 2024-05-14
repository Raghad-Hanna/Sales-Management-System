package com.raghad.sales_management_system.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import com.raghad.sales_management_system.entities.Product;

public record ProductUpdate(Integer id,
                            @NotBlank(message = "The name must be non-blank") String name,
                            String description,
                            @NotBlank(message = "The category must be non-blank") String category,
                            @Positive(message = "The quantity must be positive") int quantity,
                            @Positive(message = "The price must be positive") double price) {
    public Product toProduct() {
        return new Product(this.id, this.name, this.description, this.category, this.quantity, this.price);
    }
}
