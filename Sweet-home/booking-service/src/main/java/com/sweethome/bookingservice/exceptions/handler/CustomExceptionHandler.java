package com.sweethome.bookingservice.exceptions.handler;

import java.util.ArrayList;
import java.util.List;

import com.sweethome.bookingservice.exceptions.BadRequestException;
import com.sweethome.bookingservice.exceptions.RecordNotFoundException;
import com.sweethome.bookingservice.exceptions.dto.ErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private String NO_RECORDS_FOUND = "Invalid Booking Id";
    private String BAD_REQUEST = "Invalid mode of payment";
    
    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<Object> 
        handleRecordNotFoundException(
            RecordNotFoundException e
        ){
            // List<String> errorDetails = new ArrayList<String>();
            // errorDetails.add(
            //     // e.getLocalizedMessage()
            //     HttpStatus.NOT_FOUND.toString()
            // );
            String statusCode = HttpStatus.NOT_FOUND.toString();

            ErrorResponse response = new ErrorResponse(
                NO_RECORDS_FOUND,
                statusCode
            );
            
            
            return new ResponseEntity(
                response,
                HttpStatus.NOT_FOUND
            );
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<Object> 
        handleBadRequestException(
            BadRequestException e
        ){
            // List<String> errorDetails = new ArrayList<String>();
            // errorDetails.add(
            //     // e.getLocalizedMessage()
            //     HttpStatus.NOT_FOUND.toString()
            // );
            String statusCode = HttpStatus.BAD_REQUEST.toString();

            ErrorResponse response = new ErrorResponse(
                BAD_REQUEST,
                statusCode
            );
            
            
            return new ResponseEntity(
                response,
                HttpStatus.BAD_REQUEST
            );
    }
}