package com.cbs.middleware.service.impl;

import com.cbs.middleware.dto.ProcessingResponse;
import com.cbs.middleware.dto.TransactionRequest;
import com.cbs.middleware.dto.TransactionResponse;
import com.cbs.middleware.dto.TransactionUpdateRequest;
import com.cbs.middleware.model.Account;
import com.cbs.middleware.model.Transaction;
import com.cbs.middleware.model.TransactionUpdate;
import com.cbs.middleware.repositories.TransactionRepository;
import com.cbs.middleware.repositories.TransactionUpdateRepository;
import com.cbs.middleware.service.interfaces.TransactionService;
import com.cbs.middleware.util.JsonConverter;
import com.cbs.middleware.util.Validations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {
    @Value("${web.hook.url}")
    private String webhookUrl;

    private final TransactionRepository tranRepository;
    RestTemplate restTemplate = new RestTemplate();
    public ProcessingResponse processTransaction(TransactionRequest request, HttpServletRequest httpServletRequest) {
       log.info("Processing Request:: "+ JsonConverter.toJson(request, true));
        ProcessingResponse response = new ProcessingResponse();
        TransactionUpdateRequest webhookRequest = new TransactionUpdateRequest();
        //Tanking to bank transaction table
        Transaction tran = new Transaction()
                .setAmount(request.getAmount())
                .setTranType(request.getTranType())
                .setDebitAccountType(request.getDebitAccountType())
                .setDebitAccountNo(request.getDebitAccountNo())
                .setCreditAccountNo(request.getCreditAccountNo())
                .setCurrencyCode(request.getCurrencyCode())
                .setExchRate(request.getExchRate())
                .setPaymentDescription(request.getPaymentDescription())
                .setPaymentReference(request.getPaymentReference());

        boolean duplicate = duplicateCheck(tran.getPaymentReference());
        if(duplicate){
            tran.setResponseMessage("Duplicate Transaction");
            tran.setResponseCode("E92");
            response.setMessage("Duplicate Transaction")
                    .setStatusCode(HttpStatus.FORBIDDEN.value())
                    .setTransaction(request);
            log.info("final Response:: "+ JsonConverter.toJson(response, true));
            return response;
        }else{
          String validate = Validations.InputValidation(request);
          if(!validate.equalsIgnoreCase("success")){
              tran.setStatus("FAILED").setResponseCode("E93").setResponseMessage(validate);
              tranRepository.save(tran);
              webhookRequest.setTransactionId(tran.getPaymentReference())
                      .setPaymentReference(tran.getPaymentReference())
                      .setTransactionReference(tran.getPaymentReference())
                      .setStatus(tran.getStatus())
                      .setResponseMessage(tran.getResponseMessage())
                      .setResponseCode(tran.getResponseCode());

              String webResponse = updateWebHook(webhookRequest, httpServletRequest);

              response.setMessage(validate)
                      .setStatusCode(HttpStatus.EXPECTATION_FAILED.value());
              log.info("final Response:: "+ JsonConverter.toJson(response, true));
              return response;
          }else{
              // TODO Call Account Enquiry to Validate Account before tanking to our table
              // if accounts are validated ok continue
              tran.setStatus("SUCCESS").setResponseCode("000").setResponseMessage("Transaction Approved");
                tranRepository.save(tran);

              webhookRequest.setTransactionId(tran.getPaymentReference())
                      .setPaymentReference(tran.getPaymentReference())
                      .setTransactionReference(tran.getPaymentReference())
                      .setStatus(tran.getStatus())
                      .setResponseMessage(tran.getResponseMessage())
                      .setResponseCode(tran.getResponseCode());

              String webResponse = updateWebHook(webhookRequest, httpServletRequest);
                // make the transfer
              response.setMessage("Transaction Approved")
                        .setStatusCode(HttpStatus.OK.value())
                        .setTransaction(request);
              log.info("final Response:: "+ JsonConverter.toJson(response, true));
              return response;
        }
        }
    }
    public boolean duplicateCheck(String requestId) {
        Optional<Transaction> transaction = tranRepository.findByPaymentReference(requestId);
        if(transaction.isPresent()){
            return true;
        }else {
            return false;
        }
    }

    public String updateWebHook(TransactionUpdateRequest request, HttpServletRequest httpServletRequest){
        log.info("Updating webhook::::::::");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Api-Key", httpServletRequest.getHeader("Api-Key"));
        try{
            HttpEntity<TransactionUpdateRequest> requestEntity = new HttpEntity<>(request, headers);
            return restTemplate.postForObject(webhookUrl, requestEntity, String.class);
        }catch(Exception e){
            log.info("Error Occurred:: {}", ExceptionUtils.getStackTrace(e));
            return "Processing Failed";
        }
    }

}
