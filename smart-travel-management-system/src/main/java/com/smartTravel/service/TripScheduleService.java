package com.smartTravel.service;

import com.smartTravel.dto.TripScheduleDto;
import com.smartTravel.entity.TripScheduleEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public interface TripScheduleService {
    public ResponseEntity<String> addTripScheduleDetails(TripScheduleDto tripScheduleDto);
    public ResponseEntity<List<TripScheduleEntity>>searchTripDetails(String source, String destination, LocalDate tripDate);
    public ResponseEntity<TripScheduleEntity> getTripDetailsByTripId(long tripId);
}
