package com.sweethome.bookingservice.controller;

import com.sweethome.bookingservice.entity.Booking;
import com.sweethome.bookingservice.service.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/booking")
@Slf4j
public class BookingController {
    
    @Autowired
    private BookingService bookingService;

    @PostMapping("/")
    public Booking saveBooking(
        @RequestBody Booking booking
    ){
        log.info("Inside saveBooking method of BookingController");
        return bookingService.saveBooking(booking);
    }

    @GetMapping("/{id}")
    public Booking findbyBookingId(@PathVariable("id") Integer bookingId){
        log.info("Inside findBookingById method of BookingController");
        return bookingService.findByBookingId(bookingId);
    }
}
