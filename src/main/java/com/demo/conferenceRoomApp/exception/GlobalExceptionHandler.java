package com.demo.conferenceRoomApp.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Optional;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RoomBookingException.class)
    public ResponseEntity<String> handleRoomBookingException(RoomBookingException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(RoomsNotAvailableException.class)
    public ResponseEntity<String> handleRoomsNotAvailableException(RoomsNotAvailableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleException(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                              HttpHeaders headers,
                                                              HttpStatusCode status,
                                                              WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        Optional.ofNullable(ex.getSupportedHttpMethods()).map(t -> builder.append(t).append(", "));

        return ResponseEntity.status(status)
                .body(builder);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

}