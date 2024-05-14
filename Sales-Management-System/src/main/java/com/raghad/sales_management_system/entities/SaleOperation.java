package com.raghad.sales_management_system.entities;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SaleOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate creationDate;
    @OneToOne
    @JoinColumn(name = "buyer_id")
    private Client buyer;
    @OneToOne
    @JoinColumn(name = "seller_id")
    private Client seller;
    @OneToMany(mappedBy = "saleOperation", cascade = CascadeType.ALL)
    @Size(min = 1, message = "The sale operation must have at least one sale transaction")
    private List<SaleTransaction> saleTransactions = new ArrayList<>();
    private double totalPrice;

    public SaleOperation(Integer id, List<SaleTransaction> saleTransactions) {
        this.id = id;
        this.saleTransactions = saleTransactions;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setBuyer(Client buyer) {
        this.buyer = buyer;
    }

    public void setSeller(Client seller) {
        this.seller = seller;
    }

    public void setSaleTransactions(List<SaleTransaction> saleTransactions) {
        this.saleTransactions = saleTransactions;
        for(var transaction: this.saleTransactions) {
            transaction.setSaleOperation(this);
        }
    }

    public void calculateTotalPrice() {
        for(var saleTransaction: this.saleTransactions) {
            this.totalPrice += saleTransaction.getTotalPrice();
        }
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getId() {
        return this.id;
    }

    public LocalDate getCreationDate() {
        return this.creationDate;
    }

    public Client getBuyer() {
        return this.buyer;
    }

    public Client getSeller() {
        return this.seller;
    }

    public List<SaleTransaction> getSaleTransactions() {
        return this.saleTransactions;
    }

    public double getTotalPrice() {
        return this.totalPrice;
    }
}
