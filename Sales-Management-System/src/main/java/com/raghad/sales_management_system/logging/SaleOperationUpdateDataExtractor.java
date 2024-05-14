package com.raghad.sales_management_system.logging;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import com.raghad.sales_management_system.repositories.SaleOperationRepository;
import com.raghad.sales_management_system.entities.SaleOperation;

@Aspect
@Component
public class SaleOperationUpdateDataExtractor {
    private final SaleOperationRepository saleOperationRepository;

    public SaleOperationUpdateDataExtractor(SaleOperationRepository saleOperationRepository) {
        this.saleOperationRepository = saleOperationRepository;
    }

    @Around("@annotation(com.raghad.sales_management_system.annotations.SaleOperationUpdateLogging)")
    public Object extractSaleOperationUpdateData(ProceedingJoinPoint joinPoint) throws Throwable {
        SaleOperation newSaleOperation = (SaleOperation) joinPoint.getArgs()[0];
        String saleOperationName = newSaleOperation.getClass().getSimpleName();
        Integer saleOperationId = newSaleOperation.getId();

        Optional<SaleOperation> oldSaleOperation = this.saleOperationRepository.findById(saleOperationId);
        SaleOperation existingSaleOperation;

        List<ResourceUpdatedField> updatedFields = new ArrayList<>();
        UpdatedResourceData resourceData = null;

        if(oldSaleOperation.isPresent()) {
            existingSaleOperation = oldSaleOperation.get();

            if(existingSaleOperation.getTotalPrice() != newSaleOperation.getTotalPrice())
                updatedFields.add(new ResourceUpdatedField("Total Price", existingSaleOperation.getTotalPrice(), newSaleOperation.getTotalPrice()));
            resourceData = new UpdatedResourceData(saleOperationName, saleOperationId, updatedFields);
        }

        Object returnedValue = joinPoint.proceed();

        if(updatedFields.size() > 0)
            ResourceUpdateLogger.logResourceUpdate(resourceData);

        return returnedValue;
    }
}
