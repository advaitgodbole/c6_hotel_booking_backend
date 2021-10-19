package com.sweethome.bookingservice.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer bookingId;
    private Date fromDate;
    private Date toDate;
    private String aadharNumber;
    private Integer numberOfRooms;
    private String roomNumbers;
    @Column(columnDefinition = "integer default 0")
    private Integer roomPrice;
    @Column(columnDefinition = "integer default 0")
    private int transactionId;

}
