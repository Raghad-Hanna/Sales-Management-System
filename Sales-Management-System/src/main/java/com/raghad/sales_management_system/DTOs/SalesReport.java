package com.raghad.sales_management_system.DTOs;

import java.util.List;

import com.raghad.sales_management_system.entities.Product;
import com.raghad.sales_management_system.entities.Client;

public record SalesReport(int saleCount,
                          double totalRevenue,
                          List<Product> topSellingProducts,
                          List<Client> topPerformingSellers) {
}
