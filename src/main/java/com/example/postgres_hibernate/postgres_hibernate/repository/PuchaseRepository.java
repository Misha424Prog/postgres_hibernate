package com.example.postgres_hibernate.postgres_hibernate.repository;

import com.example.postgres_hibernate.postgres_hibernate.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PuchaseRepository extends JpaRepository<Purchase,Long> {
}
