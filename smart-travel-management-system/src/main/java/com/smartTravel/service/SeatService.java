package com.smartTravel.service;

import com.smartTravel.entity.SeatEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SeatService {
    public ResponseEntity<List<SeatEntity>> getSeats(Long tripId);
}
