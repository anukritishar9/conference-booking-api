package com.demo.conferenceRoomApp.service;

import com.demo.conferenceRoomApp.exception.RoomsNotAvailableException;
import com.demo.conferenceRoomApp.exception.enums.ErrorCodes;
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

@Service
@RequiredArgsConstructor
public class RoomBookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ConferenceRoomRepository conferenceRoomRepository;
    private final RoomBookingValidator roomBookingValidator;

    @Override
    @Transactional
    public void bookConferenceRoom(BookingRequest bookingRequest) {
        validateBookingRequest(bookingRequest);

        List<ConferenceRoom> availableRooms = getAvailableRooms(bookingRequest.getStartDateTime(), bookingRequest.getEndDateTime());
        ConferenceRoom conferenceRoom = findBestFitConferenceRoom(availableRooms, bookingRequest.getParticipants());

        Booking booking = Booking.builder()
                .conferenceRoom(conferenceRoom)
                .participants(bookingRequest.getParticipants())
                .startTime(bookingRequest.getStartDateTime())
                .endTime(bookingRequest.getEndDateTime())
                .build();

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
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RoomsNotAvailableException(ErrorCodes.BOOKING_ID_NOT_FOUND));

        bookingRepository.deleteById(bookingId);
    }

    private List<ConferenceRoom> getBookedConferenceRooms(LocalDateTime startTime, LocalDateTime endTime) {
        List<Booking> bookings = bookingRepository.findByStartTimeBetween(startTime, endTime);
        return bookings.stream().map(Booking::getConferenceRoom).toList();
    }

    private List<ConferenceRoom> getAvailableRooms(LocalDateTime startTime, LocalDateTime endTime) {
        List<ConferenceRoom> bookedConferenceRooms = getBookedConferenceRooms(startTime, endTime);
        List<ConferenceRoom> allConferenceRooms = conferenceRoomRepository.findAll();

        return allConferenceRooms.stream()
                .filter(conferenceRoom -> !bookedConferenceRooms.contains(conferenceRoom))
                .toList();
    }

    private ConferenceRoom findBestFitConferenceRoom(List<ConferenceRoom> availableRooms, int participants) {
        return availableRooms.stream()
                .filter(room -> participants <= room.getCapacity())
                .min(Comparator.comparing(ConferenceRoom::getCapacity))
                .orElseThrow(() -> new RoomsNotAvailableException(ErrorCodes.ROOM_CAPACITY_CONSTRAINT));
    }

    private void validateBookingRequest(BookingRequest bookingRequest) {
        if (bookingRequest.getParticipants() <= 0) {
            throw new RoomsNotAvailableException(ErrorCodes.NUMBER_OF_PARTICIPANTS_ERROR);
        }
        validateBookingInterval(bookingRequest.getStartDateTime(), bookingRequest.getEndDateTime());

        if (roomBookingValidator.isMaintenanceTimeConflict(bookingRequest.getStartDateTime(), bookingRequest.getEndDateTime())) {
            throw new RoomsNotAvailableException(ErrorCodes.MAINTENANCE_TIME_ERROR);
        }
    }

    private void validateBookingInterval(LocalDateTime startTime, LocalDateTime endTime) {
        if (!roomBookingValidator.isBookingDateValid(startTime, endTime)) {
            throw new RoomsNotAvailableException(ErrorCodes.DATE_CONSTRAINT_ERROR);
        }
            if (!roomBookingValidator.isValidBookingInterval(startTime, endTime)) {
                throw new RoomsNotAvailableException(ErrorCodes.TIME_CONSTRAINT_ERROR);
            }
        }

    }