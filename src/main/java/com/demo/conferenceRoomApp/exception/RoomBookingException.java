package com.demo.conferenceRoomApp.exception;

import com.demo.conferenceRoomApp.exception.enums.ErrorCodes;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoomBookingException extends RuntimeException {
    private String message;
    private String errorCode;
    public  RoomBookingException(ErrorCodes errorCode) {
        this.message = errorCode.getMessage();
        this.errorCode = errorCode.getErrorCode();
    }

}
