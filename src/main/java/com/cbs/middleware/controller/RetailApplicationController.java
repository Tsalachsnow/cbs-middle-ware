package com.cbs.middleware.controller;

import com.cbs.middleware.dto.TransactionRequest;
import com.cbs.middleware.dto.TransactionResponse;
import com.cbs.middleware.dto.TransactionUpdateRequest;
import com.cbs.middleware.dto.TransactionUpdateResponse;
import com.cbs.middleware.service.interfaces.RetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transaction")
public class RetailApplicationController {

    private final RetailService service;

    @PostMapping("/initiate")
    public ResponseEntity<TransactionResponse> initiateTransaction(@RequestBody TransactionRequest request) {
        TransactionResponse transaction = service.initiateTransaction(request);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> receiveWebhookNotification(@RequestBody TransactionUpdateRequest update) {
        service.processTransactionUpdate(update);
        return ResponseEntity.ok("Webhook notification received successfully");
    }

    @GetMapping("/get-transaction-status/{paymentReference}")
    public ResponseEntity<TransactionUpdateResponse> getTransactionStatus(@PathVariable String  paymentReference) {
        TransactionUpdateResponse response = service.getStatus(paymentReference);
        return ResponseEntity.ok(response);
    }
}
