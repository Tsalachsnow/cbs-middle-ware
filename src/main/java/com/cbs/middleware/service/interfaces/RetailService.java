package com.cbs.middleware.service.interfaces;

import com.cbs.middleware.dto.TransactionRequest;
import com.cbs.middleware.dto.TransactionResponse;
import com.cbs.middleware.dto.TransactionUpdateRequest;
import com.cbs.middleware.dto.TransactionUpdateResponse;

import javax.servlet.http.HttpServletRequest;

public interface RetailService {

    TransactionResponse initiateTransaction(TransactionRequest request, HttpServletRequest httpServletRequest);
    void processTransactionUpdate(TransactionUpdateRequest update, HttpServletRequest httpServletRequest);
    TransactionUpdateResponse getStatus(String paymentReference, HttpServletRequest httpServletRequest);
}
