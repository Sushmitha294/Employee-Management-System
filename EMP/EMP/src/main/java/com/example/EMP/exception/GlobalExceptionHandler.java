package com.example.EMP.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    //handle the specific exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest) {

        ErrorDetails errorDetails=new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    //handle the specific exception
    @ExceptionHandler(APIException.class)
    public ResponseEntity<?> handleAPIException(ResourceNotFoundException exception, WebRequest webRequest) {

        ErrorDetails errorDetails=new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }





    //handle global exception

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException
            (Exception exception, WebRequest webRequest) {

        ErrorDetails errorDetails=new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
