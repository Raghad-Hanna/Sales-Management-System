package com.raghad.sales_management_system.services;

import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.raghad.sales_management_system.repositories.ProductRepository;
import com.raghad.sales_management_system.entities.Product;
import com.raghad.sales_management_system.DTOs.ProductReport;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ProductReportService {
    private final ProductRepository productRepository;
    private List<Product> products;

    public ProductReportService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductReport generateProductReport() {
        this.products = this.productRepository.findAll();
        int productCount = this.products.size();
        Map<String, List<Integer>> classifiedProductsByCategory = this.classifyProductsByCategory();
        List<Integer> recentlyCreatedProducts = this.findRecentlyCreatedProducts();
        return new ProductReport(productCount, classifiedProductsByCategory, recentlyCreatedProducts);
    }

    private Map<String, List<Integer>> classifyProductsByCategory() {
        Map<String, List<Integer>> classifiedProductsByCategory = new HashMap<>();
        for(var product: this.products) {
            String category = product.getCategory();
            int id = product.getId();

            if(classifiedProductsByCategory.containsKey(category)) {
                classifiedProductsByCategory.get(category).add(id);
            }
            else {
                List<Integer> productsIds = new ArrayList<>();
                productsIds.add(id);
                classifiedProductsByCategory.put(category, productsIds);
            }
        }
        return classifiedProductsByCategory;
    }

    private List<Integer> findRecentlyCreatedProducts() {
        return this.products
                .stream()
                .sorted((firstProduct, secondProduct) -> secondProduct.getCreationDate().compareTo(firstProduct.getCreationDate()))
                .limit(3)
                .map(product -> product.getId())
                .collect(Collectors.toList());
    }
}
