package com.demo.conferenceRoomApp.service;

import com.demo.conferenceRoomApp.model.entity.ConferenceRoom;
import com.demo.conferenceRoomApp.repository.ConferenceRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConferenceRoomServiceImpl implements ConferenceRoomService {

    private final ConferenceRoomRepository conferenceRoomRepository;

    @Override
    public List<ConferenceRoom> getAllRooms() {
        return conferenceRoomRepository.findAll();
    }
}
