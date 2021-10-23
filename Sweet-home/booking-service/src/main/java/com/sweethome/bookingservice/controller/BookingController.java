package com.sweethome.bookingservice.controller;

import com.sweethome.bookingservice.VO.BookingTransactionVO;
import com.sweethome.bookingservice.entity.Booking;
import com.sweethome.bookingservice.exceptions.RecordNotFoundException;
import com.sweethome.bookingservice.service.BookingService;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
        // try {
        //     Booking booking = 
        //     bookingService.findByBookingId(bookingId);
        //     return booking;
        // } 
        // catch (RecordNotFoundException e){
        //     throw new ResponseStatusException(
        //         HttpStatus.NOT_FOUND,
        //         "Booking not found",
        //         e
        //     );
        // }
        
        return bookingService.findByBookingId(bookingId);
    }

    @GetMapping("/testExceptionHandling")
    public String testExceptionHandling(@RequestParam int code){
        log.info("Inside testExceptionHandling method of BookingController");
        switch (code){
            case 404:
                throw new RecordNotFoundException(
                    "Record not found"
                );
            default:
                return "Yeah...No Exception.";
        }
        
        // return bookingService.findByBookingId(bookingId);
    }

    @PostMapping("/{id}/transaction")
    public Booking sendPaymentDetailsAndSaveBooking(
        @PathVariable("id") Integer bookingId,
        @RequestBody BookingTransactionVO restPayload
    ){
        log.info("Inside sendPaymentDetailsAndSaveBooking method of BookingController");

        return bookingService.sendPaymentDetailsAndSaveBooking(bookingId, restPayload);
    }
}
