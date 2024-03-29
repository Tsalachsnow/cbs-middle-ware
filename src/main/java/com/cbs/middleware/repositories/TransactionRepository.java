package com.cbs.middleware.repositories;

import com.cbs.middleware.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
   Optional<Transaction> findByPaymentReference(String requestId);
}
