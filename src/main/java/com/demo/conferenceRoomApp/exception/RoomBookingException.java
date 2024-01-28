package com.demo.conferenceRoomApp.exception;

import com.demo.conferenceRoomApp.exception.enums.ErrorCodes;

import java.io.Serial;
import java.util.Objects;

public class RoomBookingException extends RuntimeException {
    private String errorCode;
    private String message;
    public RoomBookingException(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;

    }

}
