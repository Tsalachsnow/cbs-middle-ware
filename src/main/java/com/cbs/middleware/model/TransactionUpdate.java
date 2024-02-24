package com.cbs.middleware.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TransactionUpdate {
    private String transactionId;
    private String status;
}
