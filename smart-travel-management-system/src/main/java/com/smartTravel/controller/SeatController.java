package com.smartTravel.controller;

import com.smartTravel.entity.SeatEntity;
import com.smartTravel.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seats")
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "https://swapnalibanagar.github.io")
public class SeatController {
    @Autowired
    SeatService seatService;

    @GetMapping("/{tripId}")
    public ResponseEntity<List<SeatEntity>> getSeats(@PathVariable Long tripId) {

        return seatService.getSeats(tripId);
    }
}
