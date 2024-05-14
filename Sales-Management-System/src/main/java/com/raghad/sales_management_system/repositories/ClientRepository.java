package com.raghad.sales_management_system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raghad.sales_management_system.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}
