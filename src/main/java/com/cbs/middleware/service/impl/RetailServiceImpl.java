package com.cbs.middleware.service.impl;

import com.cbs.middleware.dto.*;
import com.cbs.middleware.model.TransactionUpdate;
import com.cbs.middleware.repositories.TransactionRepository;
import com.cbs.middleware.repositories.TransactionUpdateRepository;
import com.cbs.middleware.service.interfaces.RetailService;
import com.cbs.middleware.util.JsonConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class RetailServiceImpl implements RetailService {
    @Value("${transaction.process.url}")
    private String processingUrl;
    private final TransactionRepository tranRepository;
    private final TransactionUpdateRepository webhookRepository;
    RestTemplate restTemplate = new RestTemplate();

    public TransactionResponse initiateTransaction(TransactionRequest request, HttpServletRequest httpServletRequest) {
        log.info("Initiate Transaction Request:: "+ JsonConverter.toJson(request, true));
        TransactionResponse response = new TransactionResponse();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Api-Key", httpServletRequest.getHeader("Api-Key"));
        ProcessingResponse resp = null;
        try{
            HttpEntity<TransactionRequest> requestEntity = new HttpEntity<>(request, headers);
            resp = restTemplate.postForObject(processingUrl, requestEntity, ProcessingResponse.class);
        }catch(Exception e){
            log.info("Error Occurred:: {}", ExceptionUtils.getStackTrace(e));
            response.setResponseCode("E04").setResponseMessage("Processing Failed");
            return response;
        }
            if(resp.getStatusCode() == 200){
                response.setResponseCode("000").setResponseMessage(resp.getMessage())
                        .setPaymentReference(resp.getTransaction().getPaymentReference());
            }else{
                response.setResponseCode("E04").setResponseMessage(resp.getMessage());
            }
        log.info("Initiate Transaction Response:: "+ JsonConverter.toJson(response, true));
            return response;
    }

    public void processTransactionUpdate(TransactionUpdateRequest update, HttpServletRequest httpServletRequest) {
        log.info("Webhook Request:: "+ JsonConverter.toJson(update, true));
        // Mock implementation to process transaction update
        TransactionUpdate transactionUpdate = new TransactionUpdate()
                .setTransactionId(update.getTransactionId())
                .setTransactionReference(update.getTransactionReference())
                .setStatus(update.getStatus()).setResponseCode(update.getResponseCode())
                .setResponseMessage(update.getResponseMessage())
                .setPaymentReference(update.getPaymentReference())
                .setTranDate(LocalDateTime.now())
                .setCountryCode(update.getCountryCode());
        log.info("Request for webhook update:: "+ JsonConverter.toJson(transactionUpdate, true));
        try{
            webhookRepository.save(transactionUpdate);
        }catch(Throwable e){
          log.info("Error occurred:: " + ExceptionUtils.getStackTrace(e));
        }
        System.out.println("Received transaction update: " + update);
    }

    public TransactionUpdateResponse getStatus(String paymentReference, HttpServletRequest httpServletRequest){

        TransactionUpdateResponse response = new TransactionUpdateResponse();
        log.info("paymentReference:: "+ paymentReference);
        TransactionUpdate transactionUpdate = webhookRepository.findByPaymentReference(paymentReference);
        log.info("Transaction Update Returned:: "+ JsonConverter.toJson(transactionUpdate, true));
        if(null == transactionUpdate){
            return response.setResponseCode("E92")
                    .setResponseMessage("Payment Reference Not Found");
        }
        response.setResponseCode(transactionUpdate.getResponseCode())
                    .setResponseMessage(transactionUpdate.getResponseMessage())
                    .setStatus(transactionUpdate.getStatus())
                    .setTransactionId(transactionUpdate.getTransactionId());
        log.info("Transaction Update Response:: "+ JsonConverter.toJson(response, true));
        return response;
    }

}
