package com.raghad.sales_management_system.DTOs;

import java.util.List;
import java.util.Map;

import com.raghad.sales_management_system.entities.Client;

public record ClientReport(int clientCount,
                           List<Client> topSpendingBuyers,
                           Map<Integer, Integer> buyersSaleOperationsCount,
                           Map<String, List<Integer>> clientsGeographicalDistribution) {
}
