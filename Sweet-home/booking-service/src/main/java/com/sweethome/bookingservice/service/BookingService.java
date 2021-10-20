package com.sweethome.bookingservice.service;

import com.sweethome.bookingservice.entity.Booking;
import com.sweethome.bookingservice.repository.BookingRepository;

import java.sql.Date;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;

import com.sweethome.bookingservice.VO.BookingTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookingService {
    
    @Autowired
    private BookingRepository bookingRepository;

    // @Autowired
    // private RestTemplate restTemplate;

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
        return bookingRepository.save(booking);
    }

    public Booking findByBookingId(Integer bookingId) {
        log.info("Inside findBookingById method of BookingService");
        return bookingRepository.findByBookingId(bookingId);
    }

    public BookingTransaction getTransactionIdForBooking(Integer bookingId){
        log.info("Inside getTransactionIdForBooking of BookingService");
        BookingTransaction bookingTransaction = new BookingTransaction();
        Booking booking = bookingRepository.findByBookingId(bookingId);

        // TransactionDetailsEntity
        return bookingTransaction;
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
