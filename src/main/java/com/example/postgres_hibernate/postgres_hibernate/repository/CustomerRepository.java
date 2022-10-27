package com.example.postgres_hibernate.postgres_hibernate.repository;

import com.example.postgres_hibernate.postgres_hibernate.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByNameContaining(String name);
}
