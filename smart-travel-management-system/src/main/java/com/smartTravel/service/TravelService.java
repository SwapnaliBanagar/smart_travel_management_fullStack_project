package com.smartTravel.service;

import com.smartTravel.dto.TravelDto;
import com.smartTravel.entity.TravelEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public interface TravelService {

    public ResponseEntity<String> addTravelDetails( TravelDto travelDto);
    public ResponseEntity<TravelEntity>getTravelById(String travelNumber);
    public ResponseEntity<List<TravelEntity>> getAllTravelDetails();
    public ResponseEntity<TravelEntity>getTravelDetailsByTripId(long tripId);
    public ResponseEntity<String> updateById(String travelNumber,TravelDto travelDto);
    public ResponseEntity<String>updateTravelsDetails(String travelNumber,TravelDto travelDto);
    public ResponseEntity<String>deleteTravelDetails(String travelNumber);
}
