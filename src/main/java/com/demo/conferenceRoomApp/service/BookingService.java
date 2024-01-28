package com.demo.conferenceRoomApp.service;


import com.demo.conferenceRoomApp.model.dto.BookingRequest;
import com.demo.conferenceRoomApp.model.entity.Booking;
import com.demo.conferenceRoomApp.model.entity.ConferenceRoom;

import java.time.LocalDateTime;
import java.util.List;


public interface BookingService {
    void bookConferenceRoom(BookingRequest bookingRequest);
    List<Booking> getAllBookings();
    List<ConferenceRoom>  getAvailableConferenceRooms(LocalDateTime startTime,
                                                      LocalDateTime endTime);
    void deleteBooking(Long bookingId);
}
