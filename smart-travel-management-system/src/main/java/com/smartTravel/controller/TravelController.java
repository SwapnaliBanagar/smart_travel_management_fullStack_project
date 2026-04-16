package com.smartTravel.controller;

import com.smartTravel.dto.TravelDto;
import com.smartTravel.entity.TravelEntity;
import com.smartTravel.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/travel")
//@CrossOrigin(origins = "http://localhost:4200")
    @CrossOrigin(origins = "https://swapnalibanagar.github.io")
public class TravelController {
    @Autowired
    TravelService travelService;

    @PostMapping("/add")
    public ResponseEntity<String> addTravelDetails(@RequestBody TravelDto travelDto) {
        return travelService.addTravelDetails(travelDto);
    }

    @GetMapping("/getTravelById/{id}")
    public ResponseEntity<TravelEntity> getTravelById(@PathVariable("id") String travelNumber) {
        return travelService.getTravelById(travelNumber);
    }

    @GetMapping("/getAllTravelDetails")
    public ResponseEntity<List<TravelEntity>> getAllTravelDetails() {
        return travelService.getAllTravelDetails();
    }

    @GetMapping("/getTravelByTripId/{tripId}")
    public ResponseEntity<TravelEntity>getTravelDetailsByTripId(@PathVariable long tripId)
    {
        System.out.println(tripId);
         return travelService.getTravelDetailsByTripId(tripId);
    }



    @PutMapping("/updateById/{id}")
    public ResponseEntity<String> updateById(@PathVariable("id") String travelNumber, @RequestBody TravelDto travelDto) {
        return travelService.updateById(travelNumber, travelDto);
    }

    @PatchMapping("/updateDetails/{id}")
    public ResponseEntity<String> updateTravelsDetails(@PathVariable("id") String travelNumber, @RequestBody TravelDto travelDto) {
        return travelService.updateTravelsDetails(travelNumber, travelDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTravelDetails(@PathVariable("id") String travelNumber) {
        return travelService.deleteTravelDetails(travelNumber);
    }
}
