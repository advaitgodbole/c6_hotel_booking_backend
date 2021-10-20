package com.sweethome.bookingservice.VO;

import com.sweethome.bookingservice.entity.Booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingTransactionVO {
    
    // private TransactionDetailsEntity transactionDetailsEntity;
    // private Booking booking;
    // private Integer transactionId;
    private String paymentMode;
    private Integer bookingId;
    private String upiId;
    private String cardNumber;
    
}