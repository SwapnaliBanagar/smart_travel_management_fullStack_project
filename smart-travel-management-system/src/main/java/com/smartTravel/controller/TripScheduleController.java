package com.smartTravel.controller;

import com.smartTravel.dto.TripScheduleDto;
import com.smartTravel.entity.TripScheduleEntity;
import com.smartTravel.service.TripScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/trip")
public class TripScheduleController {
    @Autowired
    TripScheduleService tripScheduleService;

    @PostMapping("/add")
    public ResponseEntity<String> addTripScheduleDetails(@RequestBody TripScheduleDto tripScheduleDto) {
        return tripScheduleService.addTripScheduleDetails(tripScheduleDto);
    }

    @GetMapping("/by-location")
    public ResponseEntity<List<TripScheduleEntity>> searchTripDetails(@RequestParam(required = false) String source, @RequestParam(required = false) String destination, @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate tripDate) {
        return tripScheduleService.searchTripDetails(source, destination, tripDate);
    }

    @GetMapping("/getTripDetailsByTripId/{tripId}")
    public ResponseEntity<TripScheduleEntity> getTripDetailsByTripId(@PathVariable long tripId) {
        return tripScheduleService.getTripDetailsByTripId(tripId);
    }
}

