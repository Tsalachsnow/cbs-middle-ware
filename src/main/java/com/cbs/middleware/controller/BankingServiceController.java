package com.cbs.middleware.controller;

import com.cbs.middleware.model.TransactionRequest;
import com.cbs.middleware.model.TransactionUpdate;
import com.cbs.middleware.service.interfaces.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transaction")
public class BankingServiceController {

    private final TransactionService transactionService;

    @PostMapping("/process")
    public ResponseEntity<String> processTransaction(@RequestBody TransactionRequest request) {
        String response = transactionService.processTransaction(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update")
    public ResponseEntity<String> sendTransactionUpdate(@RequestBody TransactionUpdate update) {
        transactionService.sendTransactionUpdate(update);
        return ResponseEntity.ok("Transaction update sent successfully");
    }
}
