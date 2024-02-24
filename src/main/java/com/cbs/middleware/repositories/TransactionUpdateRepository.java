package com.cbs.middleware.repositories;

import com.cbs.middleware.model.TransactionUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionUpdateRepository extends JpaRepository<TransactionUpdate, Long> {
    TransactionUpdate findByPaymentReference(String paymentReference);
}
