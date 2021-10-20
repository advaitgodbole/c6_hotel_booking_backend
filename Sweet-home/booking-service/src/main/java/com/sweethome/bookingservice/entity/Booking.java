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
    @Column(nullable=true)
    private Date fromDate;
    @Column(nullable=true)
    private Date toDate;
    @Column(nullable=true)
    private String aadharNumber;
    @Column(nullable=true)
    private Integer numberOfRooms;
    @Column(nullable=true)
    private String roomNumbers;
    @Column(nullable=false,columnDefinition = "integer default 0")
    private Integer roomPrice;
    @Column(nullable=true,columnDefinition = "integer default 0")
    private int transactionId;
    @Column(nullable=true)
    private Date bookedOn;

}
