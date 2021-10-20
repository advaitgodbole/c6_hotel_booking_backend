package com.sweethome.paymentservice.repository;

import com.sweethome.paymentservice.entity.TransactionDetailsEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDetailsRepository extends JpaRepository<TransactionDetailsEntity,Integer> {
    
}
