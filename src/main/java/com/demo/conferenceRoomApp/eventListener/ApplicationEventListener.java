package com.demo.conferenceRoomApp.eventListener;
import com.demo.conferenceRoomApp.model.entity.ConferenceRoom;
import com.demo.conferenceRoomApp.model.entity.MaintenanceTime;
import com.demo.conferenceRoomApp.repository.ConferenceRoomRepository;
import com.demo.conferenceRoomApp.repository.MaintenanceTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
@Component
@RequiredArgsConstructor
public class ApplicationEventListener   {

    private final MaintenanceTimeRepository maintenanceTimeRepository;
    private final ConferenceRoomRepository conferenceRoomRepository;

    private static final List<MaintenanceTime> MAINTENANCE_TIMES = Arrays.asList(
            new MaintenanceTime(LocalTime.of(9, 0), LocalTime.of(9, 15)),
            new MaintenanceTime(LocalTime.of(13, 0), LocalTime.of(13, 15)),
            new MaintenanceTime(LocalTime.of(17, 0), LocalTime.of(17, 15))
    );

    private static final List<ConferenceRoom> CONFERENCE_ROOMS = Arrays.asList(
            new ConferenceRoom("Amaze", 3),
            new ConferenceRoom("Beauty", 7),
            new ConferenceRoom("Inspire", 12),
            new ConferenceRoom("Strive", 20)
    );

    @EventListener(ContextRefreshedEvent.class)
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.saveMaintenanceTimings();
        this.saveConferenceRoom();
    }

    private void saveMaintenanceTimings() {
            maintenanceTimeRepository.saveAll(MAINTENANCE_TIMES);
    }

    private void saveConferenceRoom() {
            conferenceRoomRepository.saveAll(CONFERENCE_ROOMS);
    }


}