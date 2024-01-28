package com.demo.conferenceRoomApp.exception.enums;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public enum ErrorCodes {

    NUMBER_OF_PARTICIPANTS_ERROR("CONF-ERR-001", "Number of Participants must be greater than OR equal to zero."),
    MAINTENANCE_TIME_ERROR("CONF-ERR-002", "Booking can't be made during maintenance hours."),
    TIME_CONSTRAINT_ERROR("CONF-ERR-003", "Booking can be done only in the intervals of 15 min."),
    BOOKING_ID_NOT_FOUND("CONF-ERR-004","Booking Id Not Found."),
    DATE_CONSTRAINT_ERROR("CONF-ERR-005", "Booking can be done only for the current date."),
    ROOM_NOT_AVAILABLE("CONF-ERR-006","Room is not available."),
    ROOM_CAPACITY_CONSTRAINT( "CONF-ERR-007","No room to accommodate the provided participants.");
    ;


    final String errorCode;
    final String message;


    ErrorCodes(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

}
