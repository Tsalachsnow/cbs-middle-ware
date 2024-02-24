package com.cbs.middleware.service.interfaces;

import com.cbs.middleware.dto.ProcessingResponse;
import com.cbs.middleware.dto.TransactionRequest;
import com.cbs.middleware.dto.TransactionResponse;
import com.cbs.middleware.dto.TransactionUpdateRequest;
import com.cbs.middleware.model.Transaction;

import javax.servlet.http.HttpServletRequest;

public interface TransactionService {
    ProcessingResponse processTransaction(TransactionRequest request, HttpServletRequest httpServletRequest);
    boolean duplicateCheck(String requestId);
}
