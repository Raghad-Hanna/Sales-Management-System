package com.raghad.sales_management_system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.time.LocalDate;

import com.raghad.sales_management_system.entities.SaleOperation;

public interface SaleOperationRepository extends JpaRepository<SaleOperation, Integer> {
    @Query("SELECT so FROM SaleOperation so WHERE so.creationDate BETWEEN :startDate AND :endDate")
    List<SaleOperation> findSaleTransactionsInDataRange(LocalDate startDate, LocalDate endDate);
}
