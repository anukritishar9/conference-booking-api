package com.demo.conferenceRoomApp.exception;

import com.demo.conferenceRoomApp.exception.enums.ErrorCodes;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoomsNotAvailableException extends RuntimeException{

    private String message;
    private String errorCode;
    public  RoomsNotAvailableException(ErrorCodes errorCode) {
        this.message = errorCode.getMessage();
        this.errorCode = errorCode.getErrorCode();
    }
}
