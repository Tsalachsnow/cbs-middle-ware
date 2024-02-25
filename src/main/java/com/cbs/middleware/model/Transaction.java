package com.cbs.middleware.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transaction_log")
@Accessors(chain = true)
@SequenceGenerator(name = "tran_id_seq", sequenceName = "tran_id_seq", allocationSize = 1, initialValue = 100)
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tran_id_seq")
    private Long id;

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
    private String status;
    private String responseMessage;
    private String responseCode;
    private LocalDateTime tranDate;
}
