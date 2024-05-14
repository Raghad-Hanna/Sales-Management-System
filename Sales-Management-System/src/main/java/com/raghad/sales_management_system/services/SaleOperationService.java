package com.raghad.sales_management_system.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.raghad.sales_management_system.annotations.SaleOperationUpdateLogging;
import java.util.List;
import java.time.LocalDate;
import java.util.Optional;

import com.raghad.sales_management_system.repositories.SaleOperationRepository;
import com.raghad.sales_management_system.repositories.SaleTransactionRepository;
import com.raghad.sales_management_system.repositories.ClientRepository;
import com.raghad.sales_management_system.repositories.ProductRepository;
import com.raghad.sales_management_system.entities.SaleOperation;
import com.raghad.sales_management_system.entities.SaleTransaction;
import com.raghad.sales_management_system.entities.Client;
import com.raghad.sales_management_system.entities.Product;
import com.raghad.sales_management_system.exceptions.ResourceNotFoundException;
import com.raghad.sales_management_system.exceptions.NotAvailableProductQuantityException;

@Service
public class SaleOperationService {
    private final SaleOperationRepository saleOperationRepository;
    private final SaleTransactionRepository saleTransactionRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    public SaleOperationService(SaleOperationRepository saleOperationRepository,
                                SaleTransactionRepository saleTransactionRepository,
                                ClientRepository clientRepository,
                                ProductRepository productRepository) {
        this.saleOperationRepository = saleOperationRepository;
        this.saleTransactionRepository = saleTransactionRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
    }

    public List<SaleOperation> getSaleOperations() {
        return this.saleOperationRepository.findAll();
    }

    @Transactional
    public SaleOperation createSaleOperation(SaleOperation saleOperation) {
        Optional<Client> buyer = this.clientRepository.findById(saleOperation.getBuyer().getId());
        Client existingBuyer = buyer.orElseThrow(() -> new ResourceNotFoundException("Non-existent client. Provide an existing client to create the sale operation"));

        Optional<Client> seller = this.clientRepository.findById(saleOperation.getSeller().getId());
        Client existingSeller = seller.orElseThrow(() -> new ResourceNotFoundException("Non-existent client. Provide an existing client to create the sale operation"));

        for(var transaction: saleOperation.getSaleTransactions()) {
            Optional<Product> product = this.productRepository.findById(transaction.getProduct().getId());
            Product existingProduct = product.orElseThrow(() -> new ResourceNotFoundException("Non-existent product. Provide an existing product to create the transaction"));

            transaction.setProduct(existingProduct);

            int requiredQuantity = transaction.getQuantity();
            int availableQuantity = existingProduct.getQuantity();

            if(requiredQuantity > availableQuantity) {
                throw new NotAvailableProductQuantityException("The required quantity is not available", existingProduct.getId());
            }
            else {
                existingProduct.setQuantity(availableQuantity - requiredQuantity);
                this.productRepository.save(existingProduct);
                transaction.calculateTotalPrice();
            }
        }

        saleOperation.setBuyer(existingBuyer);
        saleOperation.setSeller(existingSeller);

        saleOperation.setCreationDate(LocalDate.now());
        saleOperation.calculateTotalPrice();
        return this.saleOperationRepository.save(saleOperation);
    }

    @SaleOperationUpdateLogging
    @Transactional
    public SaleOperation updateSaleOperation(SaleOperation updatedSaleOperation, Integer id) {
        Optional<SaleOperation> saleOperation = this.saleOperationRepository.findById(id);
        var existingSaleOperation =
                saleOperation.orElseThrow(() -> new ResourceNotFoundException("Non-existent sale operation with an id of "
                        + updatedSaleOperation.getId()
                        + ". Provide an existing sale operation to be updated"));

        for(var transaction: updatedSaleOperation.getSaleTransactions()) {
            SaleTransaction existingSaleTransaction =
                    existingSaleOperation.getSaleTransactions()
                            .stream()
                            .filter(saleTransaction -> transaction.getId() == saleTransaction.getId()).findFirst()
                            .orElseThrow(() -> new ResourceNotFoundException("Non-existent sale transaction with an id of "
                                    + transaction.getId() + " in the sale operation with an id of "
                                    + existingSaleOperation.getId()
                                    + ". Provide a sale transaction that's part of the specified sale operation to be updated"));

            Optional<Product> product = this.productRepository.findById(existingSaleTransaction.getProduct().getId());
            var existingProduct = product.get();

            int availableQuantity = existingProduct.getQuantity();
            int oldRequiredQuantity = existingSaleTransaction.getQuantity();
            int newRequiredQuantity = transaction.getQuantity();

            if(newRequiredQuantity > (availableQuantity + oldRequiredQuantity)) {
                throw new NotAvailableProductQuantityException("The required quantity is not available", existingProduct.getId());
            }
            else {
                existingProduct.setQuantity(availableQuantity + oldRequiredQuantity - newRequiredQuantity);
                this.productRepository.save(existingProduct);

                existingSaleTransaction.setQuantity(newRequiredQuantity);

                double oldSaleTransactionTotalPrice = existingSaleTransaction.getTotalPrice();
                existingSaleTransaction.calculateTotalPrice();
                this.saleTransactionRepository.save(existingSaleTransaction);

                existingSaleOperation.setTotalPrice(existingSaleOperation.getTotalPrice() - oldSaleTransactionTotalPrice + existingSaleTransaction.getTotalPrice());
            }
        }
        this.saleOperationRepository.save(existingSaleOperation);
        return existingSaleOperation;
    }
}
