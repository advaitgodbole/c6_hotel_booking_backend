package com.sweethome.bookingservice.service;

import com.sweethome.bookingservice.entity.Booking;
import com.sweethome.bookingservice.repository.BookingRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;

import com.sweethome.bookingservice.VO.BookingTransactionVO;
import com.sweethome.bookingservice.VO.TransactionDetailsEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookingService {
    
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Booking saveBooking(Booking booking) {
        log.info("Inside saveBooking method of BookingService");
        int numberOfDays = getDifferenceDays(
            booking.getFromDate(),
            booking.getToDate()
        );
        Integer roomPrice = 1000*booking.getNumberOfRooms()*numberOfDays;
        booking.setRoomPrice(roomPrice);
        booking.setRoomNumbers(
            getRandomNumbers(
                booking.getNumberOfRooms()
            )
        );
        long millis=System.currentTimeMillis();  
        java.sql.Date nowDate=new java.sql.Date(millis);  
        booking.setBookedOn(nowDate);
        return bookingRepository.save(booking);
    }

    public Booking findByBookingId(Integer bookingId) {
        log.info("Inside findBookingById method of BookingService");
        return bookingRepository.findByBookingId(bookingId);
    }

    // public BookingTransactionVO getTransactionIdForBooking(Integer bookingId){
    //     log.info("Inside getTransactionIdForBooking of BookingService");
    //     BookingTransactionVO bookingTransaction = new BookingTransactionVO();
    //     Booking booking = bookingRepository.findByBookingId(bookingId);

    //     // TransactionDetailsEntity
    //     return bookingTransaction;
    // }

    public Booking sendPaymentDetailsAndSaveBooking(
        Integer bookingId,
        TransactionDetailsEntity restPayload
    ) {
        log.info("Inside sendPaymentDetailsAndSaveBooking of BookingService");
        // BookingTransactionVO bookingTransactionVO = new BookingTransactionVO();
        Booking booking = bookingRepository.findByBookingId(bookingId);
        // TransactionDetailsEntity restPayload = new TransactionDetailsEntity();
        restPayload.setBookingId(
            booking.getBookingId()
        );

        TransactionDetailsEntity transactionDetailsEntity = 
            restTemplate.postForObject(
                "http://PAYMENT-SERVICE/transaction", 
                restPayload,
                TransactionDetailsEntity.class 
            );

        Integer trId = transactionDetailsEntity.getTransactionId();
        booking.setTransactionId(trId);
        
        return bookingRepository.save(booking);
    }

    // Utility method
    private static ArrayList<String> getRandomNumbers(int count){
		Random rand = new Random();
		int upperBound = 100;
		ArrayList<String>numberList = new ArrayList<String>();                 
 
        for (int i=0; i<count; i++){
			numberList.add(
				String.valueOf(
					rand.nextInt(upperBound)
				)
			);                 
		}                 
 
		return numberList;
        
	}
    
    private int getDifferenceDays(Date d1, Date d2) {
        int daysdiff = 0;
        long diff = d2.getTime() - d1.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000) + 1;
        daysdiff = (int) diffDays;
        return daysdiff;
    }
}
