package com.example.batchprocessing.repository;

import com.example.batchprocessing.entity.Lock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LockRepository extends JpaRepository<Lock, String> {
}
