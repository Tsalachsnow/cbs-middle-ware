package com.cbs.middleware.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TransactionRequest {
    private String orderId;
    private double amount;
}
