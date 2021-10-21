package com.sweethome.paymentservice.controller;

import com.sweethome.paymentservice.entity.TransactionDetailsEntity;
import com.sweethome.paymentservice.service.TransactionDetailsService;
import com.sweethome.paymentservice.VO.BookingTransactionVO;

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
    public BookingTransactionVO saveTransactionDetails(
        @RequestBody BookingTransactionVO restPayload
    ){
        log.info("Inside saveTransactionDetails method of TransactionDetailsController");
        Integer payloadBookingId = restPayload.getBookingId();
        log.info(String.valueOf(payloadBookingId));
    
        TransactionDetailsEntity testEntity = 
            transactionDetailsService.findByBookingId(payloadBookingId);
        
        if (testEntity == null){
            TransactionDetailsEntity saveEntity = 
                new TransactionDetailsEntity();
            
            saveEntity.setBookingId(payloadBookingId);
            saveEntity.setCardNumber(restPayload.getCardNumber());
            saveEntity.setPaymentMode(restPayload.getPaymentMode());
            saveEntity.setUpiId(restPayload.getUpiId());
            transactionDetailsService.saveTransactionDetails(saveEntity);
            
            TransactionDetailsEntity newEntity = 
                transactionDetailsService.findByBookingId(payloadBookingId);
            
            restPayload.setTransactionId(newEntity.getTransactionId());
            return restPayload;
        }
        restPayload.setTransactionId(testEntity.getTransactionId());
        return restPayload;
    }

    @GetMapping("/{id}")
    public TransactionDetailsEntity findByTransactionId(
        @PathVariable("id") Integer transactionId
    ){
        log.info("Inside findByTransactionId method of TransactionDetailsController");
        return transactionDetailsService.findByTransactionId(transactionId);
    }
}
