package com.cbs.middleware.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transaction_status")
@Accessors(chain = true)
@SequenceGenerator(name = "stat_id_seq", sequenceName = "stat_id_seq", allocationSize = 1, initialValue = 100)
public class TransactionUpdate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tran_id_seq")
    private Long id;
    private String status;
    private String transactionId;
    private String countryCode;
    private String responseCode;
    private String responseMessage;
    private String transactionReference;
    private String paymentReference;
    private LocalDateTime tranDate;
}
