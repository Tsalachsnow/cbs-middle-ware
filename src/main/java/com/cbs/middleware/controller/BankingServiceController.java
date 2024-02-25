package com.cbs.middleware.controller;

import com.cbs.middleware.dto.ProcessingResponse;
import com.cbs.middleware.dto.TransactionRequest;
import com.cbs.middleware.service.interfaces.TransactionService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transaction")
public class BankingServiceController {

    private final TransactionService transactionService;

    @ApiOperation(value = "Endpoint for Processing Transaction", notes = "Process a transaction")
    @PostMapping("/process")
    public ResponseEntity<ProcessingResponse> processTransaction(@RequestBody TransactionRequest request, HttpServletRequest httpServletRequest) {
        ProcessingResponse response = transactionService.processTransaction(request, httpServletRequest);
        return ResponseEntity.ok(response);
    }

}
