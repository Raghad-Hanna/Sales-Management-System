package com.raghad.sales_management_system.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.raghad.sales_management_system.annotations.ProductUpdateLogging;
import java.util.List;
import java.time.LocalDate;
import java.util.Optional;

import com.raghad.sales_management_system.repositories.ProductRepository;
import com.raghad.sales_management_system.entities.Product;
import com.raghad.sales_management_system.exceptions.ResourceNotFoundException;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return this.productRepository.findAll();
    }

    @Transactional
    public Product createProduct(Product product) {
        product.setCreationDate(LocalDate.now());
        return this.productRepository.save(product);
    }

    @ProductUpdateLogging
    @Transactional
    public Product updateProduct(Product updatedProduct, Integer id) {
        Optional<Product> originalProduct = this.productRepository.findById(id);
        var existingProduct = originalProduct.orElseThrow(() -> new ResourceNotFoundException("Non-existent product with an id of "
                + id + ". Provide an existing product to be updated"));

        updatedProduct.setCreationDate(existingProduct.getCreationDate());
        return this.productRepository.save(updatedProduct);
    }

    @Transactional
    public void deleteProduct(Integer id) {
        Optional<Product> originalProduct = this.productRepository.findById(id);

        originalProduct.ifPresentOrElse(existingProduct -> this.productRepository.deleteById(id),
                () -> { throw new ResourceNotFoundException("Non-existent product with an id of "
                        + id + ". Provide an existing product to be deleted"); });
    }
}
