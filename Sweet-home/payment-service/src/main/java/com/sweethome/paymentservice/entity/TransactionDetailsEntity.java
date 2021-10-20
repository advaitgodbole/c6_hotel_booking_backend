package com.sweethome.paymentservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TransactionDetailsEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer transactionId;
    @Column(nullable=true)
    private String paymentMode;
    @Column(nullable = false)
    private Integer bookingId;
    @Column(nullable=true)
    private String upiId;
    @Column(nullable=true)
    private String cardNumber;
    
}
