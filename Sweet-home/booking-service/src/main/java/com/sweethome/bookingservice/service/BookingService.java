package com.sweethome.bookingservice.service;

import com.sweethome.bookingservice.entity.Booking;
import com.sweethome.bookingservice.exceptions.BadRequestException;
import com.sweethome.bookingservice.exceptions.RecordNotFoundException;
import com.sweethome.bookingservice.repository.BookingRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Random;
import com.sweethome.bookingservice.VO.BookingTransactionVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookingService {
    
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;
    private static final String TOPIC = "message";

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

    public Booking findByBookingId(
        Integer bookingId
    ) {
        log.info("Inside findBookingById method of BookingService");
        // try {
        //     Booking booking = 
        //     bookingRepository.findByBookingId(bookingId);
        //     return booking;
        // } 
        // catch (RecordNotFoundException e){
        //     throw new ResponseStatusException(
        //         HttpStatus.NOT_FOUND,
        //         "Booking not found",
        //         e
        //     );
        // }
        // if (booking==null) {
        //     throw RecordNotFoundException();
        // }
        boolean exists = bookingRepository.existsById(bookingId);
        if (exists){
            return bookingRepository.findByBookingId(bookingId);
        } else {
            // throw new ResponseStatusException(
            //     HttpStatus.NOT_FOUND,
            //     "Booking not found"
            // ); 
            throw new RecordNotFoundException(
                "Invalid booking id: " + bookingId
            );
        }

        // return bookingRepository.findByBookingId(bookingId);
        // return booking;
    }

    public Booking sendPaymentDetailsAndSaveBooking(
        Integer bookingId,
        BookingTransactionVO restPayload
    ) {
        log.info("Inside sendPaymentDetailsAndSaveBooking of BookingService");
        Booking booking = bookingRepository.findByBookingId(bookingId);
        
        if (booking==null){
            throw new RecordNotFoundException(
                "Invalid booking id: " + bookingId
            );
        }
        
        restPayload.setBookingId(
            booking.getBookingId()
        );
        String paymentMode = restPayload.getPaymentMode();
        
        if (
            !(
                ("UPI".equals(paymentMode))
                || ("CARD".equals(paymentMode))
            )
        ){
            throw new BadRequestException(
                "Invalid mode of payment"
            );
        }
        // log.info(restPayload.getPaymentMode());

        BookingTransactionVO bookingTransactionVO = 
            restTemplate.postForObject(
                "http://PAYMENT-SERVICE/transaction", 
                restPayload,
                BookingTransactionVO.class 
            );

        Integer trId = bookingTransactionVO.getTransactionId();
        booking.setTransactionId(trId);

        String message = 
            "Booking confirmed for user with aadhaar number: " + 
            booking.getAadharNumber() + "  |  " + 
            "Here are the booking details: " + booking.toString();
        
        kafkaTemplate.send(TOPIC,message);

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
