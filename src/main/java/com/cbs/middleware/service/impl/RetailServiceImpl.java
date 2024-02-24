package com.cbs.middleware.service.impl;

import com.cbs.middleware.dto.*;
import com.cbs.middleware.model.Transaction;
import com.cbs.middleware.model.TransactionUpdate;
import com.cbs.middleware.repositories.TransactionRepository;
import com.cbs.middleware.repositories.TransactionUpdateRepository;
import com.cbs.middleware.service.interfaces.RetailService;
import com.cbs.middleware.util.JsonConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class RetailServiceImpl implements RetailService {
    @Value("${transaction.process.url}")
    private String processingUrl;
    private final TransactionRepository tranRepository;
    private final TransactionUpdateRepository webhookRepository;
    RestTemplate restTemplate = new RestTemplate();

    public TransactionResponse initiateTransaction(TransactionRequest request) {
        log.info("Initiate Transaction Request:: "+ JsonConverter.toJson(request, true));
        TransactionResponse response = new TransactionResponse();
            ProcessingResponse resp = restTemplate.postForObject(processingUrl, request, ProcessingResponse.class);
            if(resp.getStatusCode() == 200){
                response.setResponseCode("000").setResponseMessage(resp.getMessage())
                        .setPaymentReference(resp.getTransaction().getPaymentReference());
            }else{
                response.setResponseCode("E04").setResponseMessage(resp.getMessage());
            }
        log.info("Initiate Transaction Response:: "+ JsonConverter.toJson(response, true));
            return response;
    }

    public void processTransactionUpdate(TransactionUpdateRequest update) {
        log.info("Webhook Request:: "+ JsonConverter.toJson(update, true));
        // Mock implementation to process transaction update
        TransactionUpdate transactionUpdate = new TransactionUpdate()
                .setTransactionId(update.getTransactionId())
                .setTransactionReference(update.getTransactionReference())
                .setStatus(update.getStatus()).setResponseCode(update.getResponseCode())
                .setResponseMessage(update.getResponseMessage())
                .setPaymentReference(update.getPaymentReference());
        webhookRepository.save(transactionUpdate);
        System.out.println("Received transaction update: " + update);
    }

    public TransactionUpdateResponse getStatus(String paymentReference){
        TransactionUpdateResponse response = new TransactionUpdateResponse();
        log.info("paymentReference:: "+ paymentReference);
        TransactionUpdate transactionUpdate = webhookRepository.findByPaymentReference(paymentReference);
        response.setResponseCode(transactionUpdate.getResponseCode())
                    .setResponseMessage(transactionUpdate.getResponseMessage())
                    .setStatus(transactionUpdate.getStatus())
                    .setTransactionId(transactionUpdate.getTransactionId());
        log.info("Transaction Update Response:: "+ JsonConverter.toJson(response, true));
        return response;
    }

}