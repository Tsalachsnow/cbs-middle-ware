package com.cbs.middleware.service.interfaces;

import com.cbs.middleware.dto.ProcessingResponse;
import com.cbs.middleware.dto.TransactionRequest;
import com.cbs.middleware.dto.TransactionResponse;
import com.cbs.middleware.dto.TransactionUpdateRequest;
import com.cbs.middleware.model.Transaction;

public interface TransactionService {
    ProcessingResponse processTransaction(TransactionRequest request);
    boolean duplicateCheck(String requestId);
}
