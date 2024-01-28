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
public class BookingValidator {
    private  final MaintenanceTimeRepository maintenanceTimeRepository;
    public boolean isMaintainanceTimeConflict(LocalDateTime startTime, LocalDateTime endTime){
        List<MaintenanceTime> maintenanceTimes = maintenanceTimeRepository.findAll();

        boolean isMaintenanceTime = maintenanceTimes.stream()
                .anyMatch(maintenanceTiming -> {
                    if (startTime.toLocalTime().equals(maintenanceTiming.getStartTime())) {
                        return true;
                    }
                    if (startTime.toLocalTime().isAfter(maintenanceTiming.getStartTime()) &&
                            startTime.toLocalTime().isBefore(maintenanceTiming.getEndTime())) {
                        return true;
                    }
                    return startTime.toLocalTime().isBefore(maintenanceTiming.getStartTime()) &&
                            endTime.toLocalTime().isAfter(maintenanceTiming.getStartTime());
                });

       return isMaintenanceTime;
    }
    /** Validation logic for booking intervals**/
    public boolean isValidBookingInterval(LocalDateTime startTime, LocalDateTime endTime) {
        // Check if the start and end times are in 15-minute intervals
        if (startTime.getMinute() % 15 != 0 || endTime.getMinute() % 15 != 0) {
            return false;
        }

        // Check if the end time is after the start time
        if (endTime.isBefore(startTime)) {
            return false;
        }

        // Calculate the duration of the booking
        Duration duration = Duration.between(startTime, endTime);

        // Check if the duration is a multiple of 15 minutes (900 seconds)
        return duration.getSeconds() % 900 == 0;
    }
    public   boolean isBookingValidCurrentDate(LocalDateTime startTime, LocalDateTime endTime) {

        LocalDate currentDate = LocalDate.now();
        return startTime.toLocalDate().isEqual(currentDate) && endTime.toLocalDate().isEqual(currentDate);
    }


}
