package com.sweethome.paymentservice.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingTransaction {
    
    private Integer bookingId;
    private Integer transactionId;
    
}
