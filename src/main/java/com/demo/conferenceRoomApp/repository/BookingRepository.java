package com.demo.conferenceRoomApp.repository;

import com.demo.conferenceRoomApp.model.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
    List<Booking> findByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
}
