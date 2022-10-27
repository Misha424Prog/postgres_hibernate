package com.example.postgres_hibernate.postgres_hibernate.repository;

import com.example.postgres_hibernate.postgres_hibernate.entity.Customer;
import com.example.postgres_hibernate.postgres_hibernate.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductReposotory extends JpaRepository<Product,Long> {
    List<Product> findByNameContaining(String name);
}
