package com.sweethome.paymentservice.service;

import com.sweethome.paymentservice.entity.TransactionDetailsEntity;
import com.sweethome.paymentservice.repository.TransactionDetailsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransactionDetailsService {
    
    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;

    public TransactionDetailsEntity saveTransactionDetails(
        TransactionDetailsEntity transactionDetailsEntity
    ) {
        log.info("Inside saveTransactionDetails method of TransactionDetailsService");
        return transactionDetailsRepository.save(transactionDetailsEntity);
    }

    public TransactionDetailsEntity findByTransactionId(Integer transactionId) {
        log.info("Inside findByTransactionId method of TransactionDetailsService");
        return transactionDetailsRepository.findByTransactionId(transactionId);
    }
}
