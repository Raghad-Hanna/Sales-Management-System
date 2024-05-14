package com.raghad.sales_management_system.services;

import org.springframework.stereotype.Service;
import java.util.*;
import java.time.LocalDate;
import java.util.stream.Collectors;

import com.raghad.sales_management_system.repositories.SaleOperationRepository;
import com.raghad.sales_management_system.entities.SaleOperation;
import com.raghad.sales_management_system.entities.Product;
import com.raghad.sales_management_system.entities.Client;
import com.raghad.sales_management_system.DTOs.SalesReport;

@Service
public class SalesReportService {
    private final SaleOperationRepository saleOperationRepository;
    private List<SaleOperation> saleOperations;

    public SalesReportService(SaleOperationRepository saleOperationRepository) {
        this.saleOperationRepository = saleOperationRepository;
    }

    public SalesReport generateSalesReport(String startDate, String endDate) {
        this.findSaleOperationsInDateRange(startDate, endDate);
        // saleCount is interpreted as the total number of sale operations
        int saleCount = this.saleOperations.size();
        double totalRevenue = calculateTotalRevenue();
        List<Product> topSellingProducts = this.findTopSellingProducts();
        List<Client> topPerformingSellers = this.findTopPerformingSellers();
        return new SalesReport(saleCount, totalRevenue, topSellingProducts, topPerformingSellers);
    }

    private void findSaleOperationsInDateRange(String firstDate, String secondDate) {
        this.saleOperations =
                this.saleOperationRepository.findSaleTransactionsInDataRange(LocalDate.parse(firstDate),
                        LocalDate.parse(secondDate));
    }

    private double calculateTotalRevenue() {
        return this.saleOperations.stream().mapToDouble(saleOperation -> saleOperation.getTotalPrice()).sum();
    }

    private List<Product> findTopSellingProducts() {
        Map<Product, Integer> productsToSaleCount = new HashMap<>();
        for(var operation: this.saleOperations) {
            for(var transaction: operation.getSaleTransactions()) {
                var product = transaction.getProduct();
                int totalQuantity;
                if(productsToSaleCount.containsKey(product)) {
                    int currentQuantity = productsToSaleCount.get(product);
                    totalQuantity = currentQuantity + transaction.getQuantity();
                }
                else {
                    totalQuantity = transaction.getQuantity();
                }
                productsToSaleCount.put(product, totalQuantity);
            }
        }

        List<Product> topSellingProducts =
                productsToSaleCount.entrySet()
                        .stream()
                        .sorted((firstEntry, secondEntry) -> secondEntry.getValue() - firstEntry.getValue())
                        .limit(3)
                        .map(entry -> entry.getKey())
                        .collect(Collectors.toList());

        return topSellingProducts;
    }

    private List<Client> findTopPerformingSellers() {
        Map<Client, Integer> sellersToSaleCount = new HashMap<>();
        for(var operation: this.saleOperations) {
            Client seller = operation.getSeller();
            int newCount;
            if(sellersToSaleCount.containsKey(seller)) {
                int currentCount = sellersToSaleCount.get(seller);
                newCount = ++currentCount;
            }
            else {
                newCount = 1;
            }
            sellersToSaleCount.put(seller, newCount);
        }

        List<Client> topPerformingSellers =
                sellersToSaleCount.entrySet()
                        .stream()
                        .sorted((firstEntry, secondEntry) -> secondEntry.getValue() - firstEntry.getValue())
                        .limit(3)
                        .map(entry -> entry.getKey())
                        .collect(Collectors.toList());

        return topPerformingSellers;
    }
}
