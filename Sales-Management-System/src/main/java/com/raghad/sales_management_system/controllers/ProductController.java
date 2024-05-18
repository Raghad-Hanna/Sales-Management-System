package com.raghad.sales_management_system.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import java.util.List;

import com.raghad.sales_management_system.services.ProductService;
import com.raghad.sales_management_system.entities.Product;
import com.raghad.sales_management_system.DTOs.ProductUpdate;
import com.raghad.sales_management_system.exceptions.ResourceIdsMismatchException;

@RestController
@RequestMapping(path = "api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProducts() {
        return this.productService.getProducts();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@Valid @RequestBody Product product) {
        return this.productService.createProduct(product);
    }

    @PutMapping(path = "/{id}")
    public Product updateProduct(@Valid @RequestBody ProductUpdate productUpdate, @PathVariable("id") Integer id) {
        if(productUpdate.id() != id)
            throw new ResourceIdsMismatchException("The product ids in the URI and in the request payload must match");
        return this.productService.updateProduct(productUpdate.toProduct(), id);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable("id") Integer id) {
        this.productService.deleteProduct(id);
    }
}
