package com.sweethome.paymentservice.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingTransactionVO {
    
    private String paymentMode;
    private Integer bookingId;
    private String upiId;
    private String cardNumber;
    
}
