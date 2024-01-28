package com.demo.conferenceRoomApp.service;

import com.demo.conferenceRoomApp.model.entity.MaintenanceTime;
import com.demo.conferenceRoomApp.repository.MaintenanceTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RoomBookingValidator {
    private final MaintenanceTimeRepository maintenanceTimeRepository;

    public boolean isMaintenanceTimeConflict(LocalDateTime startTime, LocalDateTime endTime) {
        List<MaintenanceTime> maintenanceTimes = maintenanceTimeRepository.findAll();

        return maintenanceTimes.stream().anyMatch(maintenanceTiming ->
                startTime.toLocalTime().equals(maintenanceTiming.getStartTime()) ||
                        (startTime.toLocalTime().isAfter(maintenanceTiming.getStartTime()) &&
                                startTime.toLocalTime().isBefore(maintenanceTiming.getEndTime())) ||
                        (startTime.toLocalTime().isBefore(maintenanceTiming.getStartTime()) &&
                                endTime.toLocalTime().isAfter(maintenanceTiming.getStartTime()))
        );
    }

    public boolean isValidBookingInterval(LocalDateTime startTime, LocalDateTime endTime) {
        return startTime.getMinute() % 15 == 0 &&
                endTime.getMinute() % 15 == 0 &&
                !endTime.isBefore(startTime) &&
                Duration.between(startTime, endTime).getSeconds() % 900 == 0;
    }

    public boolean isBookingDateValid(LocalDateTime startTime, LocalDateTime endTime) {
        LocalDate currentDate = LocalDate.now();
        return startTime.toLocalDate().isEqual(currentDate) && endTime.toLocalDate().isEqual(currentDate);
    }
}
