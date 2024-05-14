package com.raghad.sales_management_system.entities;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SaleTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotNull(message = "The sale transaction must have a product")
    private Product product;
    @NotNull(message = "The sale transaction must have a quantity")
    @Positive(message = "The quantity must be positive")
    private Integer quantity;
    private double totalPrice;
    @ManyToOne
    @JoinColumn(name = "sale_operation_id")
    private SaleOperation saleOperation;

    public SaleTransaction(Integer id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void calculateTotalPrice() {
        this.totalPrice = this.product.getPrice() * quantity;
    }

    public void setSaleOperation(SaleOperation saleOperation) {
        this.saleOperation = saleOperation;
    }

    public Integer getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
