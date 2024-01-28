package com.demo.conferenceRoomApp.service;

import com.demo.conferenceRoomApp.exception.BookingException;
import com.demo.conferenceRoomApp.exception.BookingNotFoundException;
import com.demo.conferenceRoomApp.exception.RoomsNotAvailableException;
import com.demo.conferenceRoomApp.model.dto.BookingRequest;
import com.demo.conferenceRoomApp.model.entity.Booking;
import com.demo.conferenceRoomApp.model.entity.ConferenceRoom;
import com.demo.conferenceRoomApp.repository.BookingRepository;
import com.demo.conferenceRoomApp.repository.ConferenceRoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ConferenceRoomRepository conferenceRoomRepository;
    private final BookingValidator bookingValidator;

    @Override
    @Transactional
    public void bookConferenceRoom(BookingRequest bookingRequest) {
        validateBookingRequest(bookingRequest);

        List<ConferenceRoom> availableRooms = getAvailableRooms(bookingRequest.getStartDateTime(), bookingRequest.getEndDateTime());
        if (availableRooms.isEmpty()) {
            throw new RoomsNotAvailableException("Rooms Not Available");
        }

        ConferenceRoom conferenceRoom = availableRooms.stream()
                .filter(room -> bookingRequest.getParticipants() <= room.getCapacity())
                .min(Comparator.comparing(ConferenceRoom::getCapacity))
                .orElseThrow(() -> new RoomsNotAvailableException("No Room to Accommodate the participants provided"));

        Booking booking = dtoToEntity(bookingRequest, conferenceRoom);
        bookingRepository.save(booking);

    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public List<ConferenceRoom> getAvailableConferenceRooms(LocalDateTime startTime, LocalDateTime endTime) {
        validateBookingInterval(startTime, endTime);
        return getAvailableRooms(startTime, endTime);
    }

    @Override
    public void deleteBooking(Long bookingId) {
        bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException("BookingId Not Found"));
        bookingRepository.deleteById(bookingId);
    }

    private List<ConferenceRoom> getBookedConferenceRooms(LocalDateTime startTime, LocalDateTime endTime) {
        List<Booking> bookings = bookingRepository.findByStartTimeBetween(startTime, endTime);
        return bookings.stream().map(Booking::getConferenceRoom).collect(Collectors.toList());
    }

    private List<ConferenceRoom> getAvailableRooms(LocalDateTime startTime, LocalDateTime endTime) {
        List<ConferenceRoom> bookedConferenceRooms = getBookedConferenceRooms(startTime, endTime);
        List<ConferenceRoom> allConferenceRooms = conferenceRoomRepository.findAll();

        return allConferenceRooms.stream()
                .filter(conferenceRoom -> !bookedConferenceRooms.contains(conferenceRoom))
                .collect(Collectors.toList());
    }

    private Booking dtoToEntity(BookingRequest bookingRequest, ConferenceRoom conferenceRoom) {
        return Booking.builder()
                .conferenceRoom(conferenceRoom)
                .participants(bookingRequest.getParticipants())
                .startTime(bookingRequest.getStartDateTime())
                .endTime(bookingRequest.getEndDateTime())
                .build();
    }

    private void validateBookingRequest(BookingRequest bookingRequest) {
        if (bookingRequest.getParticipants() <= 0) {
            throw new BookingException("Number of Participants must be greater than zero");
        }
        validateBookingInterval(bookingRequest.getStartDateTime(), bookingRequest.getEndDateTime());

        if (bookingValidator.isMaintainanceTimeConflict(bookingRequest.getStartDateTime(), bookingRequest.getEndDateTime())) {
            throw new BookingException("Booking cannot be done during maintenance time");
        }
    }

    private void validateBookingInterval(LocalDateTime startTime, LocalDateTime endTime) {
        if (!bookingValidator.isBookingValidCurrentDate(startTime, endTime)) {
            throw new BookingException("Booking can be done only for the current date");
        }
        if (!bookingValidator.isValidBookingInterval(startTime, endTime)) {
            throw new BookingException("Invalid Booking interval. Bookings must be in 15-minute intervals");
        }
    }
}