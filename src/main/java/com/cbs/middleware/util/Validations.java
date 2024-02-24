package com.cbs.middleware.util;

import com.cbs.middleware.dto.TransactionRequest;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class Validations {
    public String InputValidation(TransactionRequest request){
      if(request.getAmount().compareTo(BigDecimal.ZERO) < 0){
          return "invalid amount";
      }else if(request.getCreditAccountNo().length() < 4){
          return "invalid account number "+ request.getCreditAccountNo();
      }else if(request.getDebitAccountNo().length() < 4){
          return "invalid account number "+ request.getDebitAccountNo();
      }else if(null == request.getPaymentReference()){
          return "paymentReference cannot be null";
      }
      else if(null == request.getCurrencyCode()){
          return "currencyCode cannot be null";
      }else if(null == request.getPaymentDescription()){
          return "paymentDescription cannot be null";
      }else {
          return "success";
      }
    }
}
