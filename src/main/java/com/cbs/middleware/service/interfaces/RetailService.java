package com.cbs.middleware.service.interfaces;

import com.cbs.middleware.dto.TransactionRequest;
import com.cbs.middleware.dto.TransactionResponse;
import com.cbs.middleware.dto.TransactionUpdateRequest;
import com.cbs.middleware.dto.TransactionUpdateResponse;

public interface RetailService {

    TransactionResponse initiateTransaction(TransactionRequest request);
    void processTransactionUpdate(TransactionUpdateRequest update);
    TransactionUpdateResponse getStatus(String paymentReference);
}
