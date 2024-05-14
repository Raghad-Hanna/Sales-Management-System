package com.raghad.sales_management_system.services;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.raghad.sales_management_system.repositories.ClientRepository;
import com.raghad.sales_management_system.repositories.SaleOperationRepository;
import com.raghad.sales_management_system.entities.Client;
import com.raghad.sales_management_system.entities.SaleOperation;
import com.raghad.sales_management_system.DTOs.ClientReport;

@Service
public class ClientReportService {
    private final ClientRepository clientRepository;
    private final SaleOperationRepository saleOperationRepository;
    List<Client> clients;
    List<SaleOperation> saleOperations;

    public ClientReportService(ClientRepository clientRepository, SaleOperationRepository saleOperationRepository) {
        this.clientRepository = clientRepository;
        this.saleOperationRepository = saleOperationRepository;
    }

    public ClientReport generateClientReport() {
        this.clients = this.clientRepository.findAll();
        int clientCount = this.clients.size();
        List<Client> topSpendingBuyers = findTopSpendingBuyers();
        Map<Integer, Integer> buyersSaleOperationsCount = calculateBuyersSalesOperationsCount();
        Map<String, List<Integer>> clientsGeographicalDistribution = findClientsGeographicalDistribution();
        return new ClientReport(clientCount, topSpendingBuyers,
                buyersSaleOperationsCount, clientsGeographicalDistribution);
    }

    private List<Client> findTopSpendingBuyers() {
        saleOperations = this.saleOperationRepository.findAll();
        Map<Client, Double> buyersToTotalSpending = new HashMap<>();
        for(var operation: saleOperations) {
            Client buyer = operation.getBuyer();
            double totalSpending;
            if(buyersToTotalSpending.containsKey(buyer)) {
                double currentSpending = buyersToTotalSpending.get(buyer);
                totalSpending = currentSpending + operation.getTotalPrice();
            }
            else {
                totalSpending = operation.getTotalPrice();
            }
            buyersToTotalSpending.put(buyer, totalSpending);
        }

        List<Client> topSpendingBuyers = buyersToTotalSpending.entrySet()
                .stream()
                .sorted((firstEntry, secondEntry) -> (int) (secondEntry.getValue() - firstEntry.getValue()))
                .limit(3)
                .map(entry -> entry.getKey())
                .collect(Collectors.toList());

        return topSpendingBuyers;
    }

    private Map<Integer, Integer> calculateBuyersSalesOperationsCount() {
        Map<Integer, Integer> buyersSalesOperationsCount = new HashMap<>();
        for(var saleOperation: this.saleOperations) {
            Client buyer = saleOperation.getBuyer();
            int buyerSaleOperationsCount;

            if(buyersSalesOperationsCount.containsKey(buyer.getId())) {
                int currentBuyerSaleOperationsCount = buyersSalesOperationsCount.get(buyer.getId());
                buyerSaleOperationsCount = ++currentBuyerSaleOperationsCount;
            }
            else {
                buyerSaleOperationsCount = 1;
            }
            buyersSalesOperationsCount.put(buyer.getId(), buyerSaleOperationsCount);
        }
        return buyersSalesOperationsCount;
    }

    private Map<String, List<Integer>> findClientsGeographicalDistribution() {
        Map<String, List<Integer>> clientsGeographicalDistribution = new HashMap<>();
        for(var client: this.clients) {
            String address = client.getAddress();
            int id = client.getId();

            if(clientsGeographicalDistribution.containsKey(address)) {
                clientsGeographicalDistribution.get(address).add(id);
            }
            else {
                List<Integer> clientsIds = new ArrayList<>();
                clientsIds.add(id);
                clientsGeographicalDistribution.put(address, clientsIds);
            }
        }
        return clientsGeographicalDistribution;
    }
}
