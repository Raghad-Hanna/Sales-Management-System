package com.raghad.sales_management_system.entities;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate creationDate;
    @NotBlank(message = "The name must be non-blank")
    private String name;
    private String description;
    @NotBlank(message = "The category must be non-blank")
    private String category;
    @Positive(message = "The quantity must be positive")
    private int quantity;
    @Positive(message = "The price must be positive")
    private double price;

    public Product(Integer id, String name, String description, String category, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Product))
            return false;

        var anotherProduct = (Product) o;
        return anotherProduct.id == this.id;
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getId() {
        return this.id;
    }

    public LocalDate getCreationDate() {
        return this.creationDate;
    }

    public String getName() { return this.name; }

    public String getDescription() { return this.description; }

    public String getCategory() {
        return this.category;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public double getPrice() {
        return this.price;
    }
}
