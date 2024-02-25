package com.cbs.middleware.util;

import com.cbs.middleware.dto.TransactionRequest;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class Validations {
    public String InputValidation(TransactionRequest request){
        return (request.getPaymentDescription().isEmpty()|| request.getPaymentDescription() == null) ? "payment description field is required":
                request.getAmount() == null ?"amount field is required":
                        (request.getCurrencyCode() == null || request.getCurrencyCode().isEmpty())? "currency code field is required":
                                (request.getCountryCode() == null || request.getCountryCode().isEmpty())? "country code field is required":
                                        (request.getDebitAccountNo() == null || request.getDebitAccountNo().isEmpty())? "sender account number field is required":
                                                (request.getCreditAccountNo() == null || request.getCreditAccountNo().isEmpty())? "beneficiary account number field is required":
                                                        (request.getTranType() == null || request.getTranType().isEmpty())? "transaction type field is required":
                                                                (request.getExchRate() == null || request.getExchRate().compareTo(BigDecimal.ZERO) < 0)? "invalid exchange rate" :
                                                                        (request.getAmount().compareTo(BigDecimal.ZERO) < 0)? "invalid amount": (request.getCreditAccountNo().length() < 4)? "invalid account number "+ request.getCreditAccountNo():
                                                                                (request.getDebitAccountNo().length() < 4)? "invalid account number "+ request.getDebitAccountNo():"success";
    }
}
