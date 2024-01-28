package com.demo.conferenceRoomApp.repository;


import com.demo.conferenceRoomApp.model.entity.ConferenceRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConferenceRoomRepository extends JpaRepository<ConferenceRoom,Long> {
    ConferenceRoom findByCapacityLessThanEqual(int capacity);

}
