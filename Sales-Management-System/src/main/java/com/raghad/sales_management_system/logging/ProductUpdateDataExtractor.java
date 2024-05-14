package com.raghad.sales_management_system.logging;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import com.raghad.sales_management_system.repositories.ProductRepository;
import com.raghad.sales_management_system.entities.Product;

@Aspect
@Component
public class ProductUpdateDataExtractor {
    private final ProductRepository productRepository;

    public ProductUpdateDataExtractor(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Around("@annotation(com.raghad.sales_management_system.annotations.ProductUpdateLogging)")
    public Object extractProductUpdateData(ProceedingJoinPoint joinPoint) throws Throwable {
        Product newProduct = (Product) joinPoint.getArgs()[0];
        String productName = newProduct.getClass().getSimpleName();
        Integer productId = newProduct.getId();

        Optional<Product> oldProduct = this.productRepository.findById(productId);
        Product existingProduct;

        List<ResourceUpdatedField> updatedFields = new ArrayList<>();
        UpdatedResourceData resourceData = null;

        if(oldProduct.isPresent()) {
            existingProduct = oldProduct.get();

            if(existingProduct.getPrice() != newProduct.getPrice())
                updatedFields.add(new ResourceUpdatedField("Price", existingProduct.getPrice(), newProduct.getPrice()));
            if(existingProduct.getQuantity() != newProduct.getQuantity())
                updatedFields.add(new ResourceUpdatedField("quantity", existingProduct.getQuantity(), newProduct.getQuantity()));

            resourceData = new UpdatedResourceData(productName, productId, updatedFields);
        }

        Object returnedValue = joinPoint.proceed();

        if(updatedFields.size() > 0)
            ResourceUpdateLogger.logResourceUpdate(resourceData);

        return returnedValue;
    }
}
