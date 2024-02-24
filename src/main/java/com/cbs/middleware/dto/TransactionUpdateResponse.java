package com.cbs.middleware.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TransactionUpdateResponse {
    private String status;
    private String transactionId;
    private String responseCode;
    private String responseMessage;
}
