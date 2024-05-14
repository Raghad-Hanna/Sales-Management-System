package com.raghad.sales_management_system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raghad.sales_management_system.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
