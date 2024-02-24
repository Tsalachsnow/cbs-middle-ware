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
public class RetailApplicationController {

    private final TransactionService transactionService;

    @PostMapping("/initiate")
    public ResponseEntity<String> initiateTransaction(@RequestBody TransactionRequest request) {
        String transactionId = transactionService.initiateTransaction(request);
        return ResponseEntity.ok("Transaction initiated with ID: " + transactionId);
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> receiveWebhookNotification(@RequestBody TransactionUpdate update) {
        transactionService.processTransactionUpdate(update);
        return ResponseEntity.ok("Webhook notification received successfully");
    }
}
