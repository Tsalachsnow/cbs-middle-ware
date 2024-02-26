package com.cbs.middleware.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Data
@Accessors(chain = true)
public class TransactionUpdateRequest {
    private String status;
    private String transactionId;
    private String countryCode;
    private String responseCode;
    private String responseMessage;
    private String transactionReference;
    private String paymentReference;
    private String callBackUrl;
}
