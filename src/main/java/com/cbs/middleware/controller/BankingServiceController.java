package com.cbs.middleware.controller;

import com.cbs.middleware.dto.ProcessingResponse;
import com.cbs.middleware.dto.TransactionRequest;
import com.cbs.middleware.model.Account;
import com.cbs.middleware.service.interfaces.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transaction")
public class BankingServiceController {

    private final TransactionService transactionService;

    @PostMapping("/process")
    public ResponseEntity<ProcessingResponse> processTransaction(@RequestBody TransactionRequest request) {
        ProcessingResponse response = transactionService.processTransaction(request);
        return ResponseEntity.ok(response);
    }

}
