package com.cbs.middleware.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Account {
    private String accountNo;
    private String accountName;
    private String currencyCode;
    private String branchCode;
    private String availableBalance;
    private String currentBalance;
    private String accountType;
    private String accountStatus;
    private String accountClass;
}
