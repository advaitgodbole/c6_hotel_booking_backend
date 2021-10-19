package com.sweethome.bookingservice.service;

import com.sweethome.bookingservice.entity.Booking;
import com.sweethome.bookingservice.repository.BookingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookingService {
    
    @Autowired
    private BookingRepository bookingRepository;

    public Booking saveBooking(Booking booking) {
        log.info("Inside saveBooking method of BookingService");
        return bookingRepository.save(booking);
    }

    public Booking findByBookingId(Integer bookingId) {
        log.info("Inside findBookingById method of BookingService");
        return bookingRepository.findByBookingId(bookingId);
    }
}
