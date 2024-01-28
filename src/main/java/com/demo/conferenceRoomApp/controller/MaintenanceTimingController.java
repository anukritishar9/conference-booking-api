package com.demo.conferenceRoomApp.controller;
import com.demo.conferenceRoomApp.model.entity.MaintenanceTime;
import com.demo.conferenceRoomApp.service.MaintenanceTimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(MaintenanceTimingController.BASE_URI)
@Tag(name = "Maintenance Timings API")

public class MaintenanceTimingController {

    public static final String BASE_URI = "conference/api/maintenance-timings";

    private final MaintenanceTimeService maintenanceTimeService;
    @Operation(summary = "This api contains maintenance timings of the conference rooms")
    @GetMapping
    public ResponseEntity<List<MaintenanceTime>> fetchAllMaintenanceTimings() {
        List<MaintenanceTime> maintenanceTimes = maintenanceTimeService.getMaintenanceTimings();
        return ResponseEntity.ok(maintenanceTimes);
    }

}
