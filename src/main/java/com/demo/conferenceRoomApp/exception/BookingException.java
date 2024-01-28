package com.demo.conferenceRoomApp.exception;

import java.io.Serial;
import java.util.Objects;

public class BookingException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public BookingException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "BookingException: " + getMessage();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingException that = (BookingException) o;
        return Objects.equals(getMessage(), that.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMessage());
    }
}
