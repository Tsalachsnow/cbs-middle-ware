package com.cbs.middleware.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class TransactionRequest {
    private BigDecimal amount;
    private String debitAccountNo;
    private String debitAccountType;
    private String creditAccountNo;
    private String currencyCode;
    private String countryCode;
    private String paymentDescription;
    private String tranType;
    private String paymentReference;
    private BigDecimal exchRate;
}
