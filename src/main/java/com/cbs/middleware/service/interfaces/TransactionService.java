package com.cbs.middleware.service.interfaces;

import com.cbs.middleware.model.TransactionRequest;
import com.cbs.middleware.model.TransactionUpdate;

public interface TransactionService {
   String initiateTransaction(TransactionRequest request);
    void processTransactionUpdate(TransactionUpdate update);
    String processTransaction(TransactionRequest request);
    void sendTransactionUpdate(TransactionUpdate update);
}
