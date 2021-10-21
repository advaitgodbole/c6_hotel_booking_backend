package com.sweethome.paymentservice.controller;

import com.sweethome.paymentservice.entity.TransactionDetailsEntity;
import com.sweethome.paymentservice.service.TransactionDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/transaction")
@Slf4j
public class TransactionDetailsController {
    
    @Autowired
    private TransactionDetailsService transactionDetailsService;

    @PostMapping("")
    // @PostMapping("transaction")
    public TransactionDetailsEntity saveTransactionDetails(
        @RequestBody TransactionDetailsEntity transactionDetailsEntity
    ){
        log.info("Inside saveTransactionDetails method of TransactionDetailsController");
        return transactionDetailsService.saveTransactionDetails(transactionDetailsEntity);
    }

    @GetMapping("/{id}")
    public TransactionDetailsEntity findByTransactionId(
        @PathVariable("id") Integer transactionId
    ){
        log.info("Inside findByTransactionId method of TransactionDetailsController");
        return transactionDetailsService.findByTransactionId(transactionId);
    }
}
