package com.cbs.middleware.controller;

import com.cbs.middleware.dto.TransactionRequest;
import com.cbs.middleware.dto.TransactionResponse;
import com.cbs.middleware.dto.TransactionUpdateRequest;
import com.cbs.middleware.dto.TransactionUpdateResponse;
import com.cbs.middleware.service.interfaces.RetailService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transaction")
public class RetailApplicationController {

    private final RetailService service;

    @ApiOperation(value = "Endpoint for Initiating transaction", notes = "Initiate a transaction")
    @PostMapping("/initiate")
    public ResponseEntity<TransactionResponse> initiateTransaction(@RequestBody TransactionRequest request, HttpServletRequest httpServletRequest) {
        TransactionResponse transaction = service.initiateTransaction(request, httpServletRequest);
        return ResponseEntity.ok(transaction);
    }

    @ApiOperation(value = "Endpoint for Callback", notes = "webhook for receiving updated status from banking")
    @PostMapping("/webhook")
    public ResponseEntity<String> receiveWebhookNotification(@RequestBody TransactionUpdateRequest update, HttpServletRequest httpServletRequest) {
        service.processTransactionUpdate(update, httpServletRequest);
        return ResponseEntity.ok("Webhook notification received successfully");
    }

    @ApiOperation(value = "Endpoint to Get transaction Status by payment-reference", notes = "Get Transaction Status")
    @GetMapping("/get-transaction-status/{paymentReference}")
    public ResponseEntity<TransactionUpdateResponse> getTransactionStatus(@PathVariable String  paymentReference, HttpServletRequest httpServletRequest) {
        TransactionUpdateResponse response = service.getStatus(paymentReference,httpServletRequest);
        return ResponseEntity.ok(response);
    }
}
