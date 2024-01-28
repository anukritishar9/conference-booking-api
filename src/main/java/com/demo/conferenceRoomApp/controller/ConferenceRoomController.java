package com.demo.conferenceRoomApp.controller;


import com.demo.conferenceRoomApp.model.dto.BookingRequest;
import com.demo.conferenceRoomApp.model.entity.Booking;
import com.demo.conferenceRoomApp.model.entity.ConferenceRoom;
import com.demo.conferenceRoomApp.service.BookingService;
import com.demo.conferenceRoomApp.service.ConferenceRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ConferenceRoomController.BASE_URI)
@Tag(name = "Conference API")

public class ConferenceRoomController {

    public static final String BASE_URI = "conference/api/rooms";
    public static final String AVAILABLE_URI = "/available";
    public static final String BOOKING_ID = "/{booking_id}";
    public static final String BOOKED_ROOMS = "/booked";

    private final BookingService bookingService;

    private final ConferenceRoomService conferenceRoomService;

    @Operation(summary = "Fetch all conference rooms")
    @GetMapping
    public ResponseEntity<List<ConferenceRoom>> getAllConferenceRooms() {
        List<ConferenceRoom> conferenceRooms = conferenceRoomService.getAllRooms();
        return ResponseEntity.ok(conferenceRooms);
    }

    @Operation(summary = "To see all available rooms")
    @GetMapping(AVAILABLE_URI)
    public ResponseEntity<List<ConferenceRoom>> getAvailableRooms(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime endTime) {

        // Validation to ensure endTime is after startTime
        if (endTime.isBefore(startTime)) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
        List<ConferenceRoom> availableRooms = bookingService.getAvailableConferenceRooms(startTime, endTime);
        return ResponseEntity.ok(availableRooms);
    }

    @Operation(summary = "Book a conference room")
    @PostMapping
    public ResponseEntity<String> bookConferenceRoom(@Valid @RequestBody BookingRequest bookingRequest) {
        bookingService.bookConferenceRoom(bookingRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Room has been booked Successfully");
    }

    @Operation(summary = "To see all occupied rooms")
    @GetMapping(BOOKED_ROOMS)
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @Operation(summary = "To cancel a booking")
    @DeleteMapping(BOOKING_ID)
    public ResponseEntity<String> cancelBooking(@PathVariable("booking_id") Long bookingId) {
        bookingService.deleteBooking(bookingId);
        return ResponseEntity.ok("Your booked room has been vacated");
    }

}

