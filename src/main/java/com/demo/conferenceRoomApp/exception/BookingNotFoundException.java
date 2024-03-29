package com.demo.conferenceRoomApp.exception;

import com.demo.conferenceRoomApp.exception.enums.ErrorCodes;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BookingNotFoundException extends RuntimeException {

    private String errorCode;
    private String message;
    public BookingNotFoundException(ErrorCodes errorCodes) {
        this.errorCode = errorCode;
        this.message = message;

    }

}

