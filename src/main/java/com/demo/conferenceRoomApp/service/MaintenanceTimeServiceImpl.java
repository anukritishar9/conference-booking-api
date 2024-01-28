package com.demo.conferenceRoomApp.service;

import com.demo.conferenceRoomApp.model.entity.MaintenanceTime;
import com.demo.conferenceRoomApp.repository.MaintenanceTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceTimeServiceImpl implements MaintenanceTimeService {

    private final MaintenanceTimeRepository maintenanceTimeRepository;

    @Override
    public List<MaintenanceTime> getMaintenanceTimings() {
        return maintenanceTimeRepository.findAll();
    }
}
