package com.demo.conferenceRoomApp.repository;


import com.demo.conferenceRoomApp.model.entity.MaintenanceTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintenanceTimeRepository extends JpaRepository<MaintenanceTime,Long> {

}
