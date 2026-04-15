package com.smartTravel.serviceImpl;

import com.smartTravel.dto.SeatDto;
import com.smartTravel.dto.TripScheduleDto;
import com.smartTravel.entity.SeatEntity;
import com.smartTravel.entity.TravelEntity;
import com.smartTravel.entity.TripScheduleEntity;
import com.smartTravel.repository.SeatRepository;
import com.smartTravel.repository.TravelRepository;
import com.smartTravel.repository.TripScheduleRepository;
import com.smartTravel.service.TripScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TripScheduleServiceImpl implements TripScheduleService {
    @Autowired
    TripScheduleRepository tripScheduleRepository;
    @Autowired
    TravelRepository travelRepository;
    @Autowired
    private SeatRepository seatRepository;

    @Override
    public ResponseEntity<String> addTripScheduleDetails(TripScheduleDto tripScheduleDto) {

        Optional<TravelEntity> byTravelNumber =
                travelRepository.findByTravelNumber(tripScheduleDto.getTravelNumber());

        if (byTravelNumber.isPresent()) {

            TripScheduleEntity tripScheduleEntity = new TripScheduleEntity();

            tripScheduleEntity.setTripDate(tripScheduleDto.getTripDate());
            tripScheduleEntity.setSource(tripScheduleDto.getSource());
             tripScheduleEntity.setDestination(tripScheduleDto.getDestination());
             tripScheduleEntity.setSeaterSeatPrice(tripScheduleDto.getSeaterSeatPrice());
             tripScheduleEntity.setSleeperSeatPrice(tripScheduleDto.getSleeperSeatPrice());

            // save stops
            tripScheduleEntity.setStops(tripScheduleDto.getStops());

            TravelEntity travelEntity = byTravelNumber.get();
            tripScheduleEntity.setTravel(travelEntity);


// save trip
            TripScheduleEntity savedTrip =
                    tripScheduleRepository.save(tripScheduleEntity);

            // generate seats automatically
            generateSeats(savedTrip, travelEntity);

            return ResponseEntity.ok("Trip schedule added successfully");
        }

        return ResponseEntity.badRequest().body("Please first register travels");
    }
    private void generateSeats(TripScheduleEntity trip, TravelEntity travel) {

        // Lower Seater Seats
        for (int i = 1; i <= travel.getSeaterLowerSeats(); i++) {

            SeatEntity seat = new SeatEntity();
            seat.setSeatNumber("L" + i);
            seat.setStatus("AVAILABLE");
            seat.setTripSchedule(trip);

            seatRepository.save(seat);
        }

        // Upper Seater Seats
        for (int i = 1; i <= travel.getSeaterUpperSeats(); i++) {

            SeatEntity seat = new SeatEntity();
            seat.setSeatNumber("U" + i);
            seat.setStatus("AVAILABLE");
            seat.setTripSchedule(trip);

            seatRepository.save(seat);
        }

        // Sleeper Lower Seats
        for (int i = 1; i <= travel.getSleeperLowerSeats(); i++) {

            SeatEntity seat = new SeatEntity();
            seat.setSeatNumber("SL" + i);
            seat.setStatus("AVAILABLE");
            seat.setTripSchedule(trip);


            seatRepository.save(seat);
        }

        // Sleeper Upper Seats
        for (int i = 1; i <= travel.getSleeperUpperSeats(); i++) {

            SeatEntity seat = new SeatEntity();
            seat.setSeatNumber("SU" + i);
            seat.setStatus("AVAILABLE");
            seat.setTripSchedule(trip);

            seatRepository.save(seat);
        }
    }











    //<--------------------------------------------------------------------------------------->
    @Override
    public ResponseEntity<List<TripScheduleEntity>> searchTripDetails(String source, String destination, LocalDate tripDate) {
        if (source != null && destination != null && tripDate != null) {
            List<TripScheduleEntity> bySourceDestinationAndDate = tripScheduleRepository.findBySourceAndDestinationAndTripDate(source, destination, tripDate);
            if (bySourceDestinationAndDate.isEmpty()) {
                return ResponseEntity.noContent().build(); // 204 for empty list
            }
            return ResponseEntity.ok(bySourceDestinationAndDate);
        }

        if (source != null && destination == null && tripDate == null) {
            List<TripScheduleEntity> bySource = tripScheduleRepository.findBySource(source);
            if (bySource.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(bySource);
        }



        if (source == null && destination != null && tripDate == null) {
            List<TripScheduleEntity> byDestination = tripScheduleRepository.findByDestination(destination);
            if (byDestination.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(byDestination);
        }


        if (source == null && destination == null && tripDate != null) {
            List<TripScheduleEntity> byTripDate = tripScheduleRepository.findByTripDate(tripDate);
                       if (byTripDate.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(byTripDate);
        }

        if (source != null && destination != null && tripDate == null) {
            List<TripScheduleEntity> bySourceAndDestination = tripScheduleRepository.findBySourceAndDestination(source, destination);
            if (bySourceAndDestination.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(bySourceAndDestination);
        }

        if (source != null && destination == null && tripDate != null) {
            List<TripScheduleEntity> bySourceAndDate = tripScheduleRepository.findBySourceAndTripDate(source, tripDate);
            if (bySourceAndDate.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(bySourceAndDate);
        }

        if (source == null && destination != null && tripDate != null) {
            List<TripScheduleEntity> byDestinationAndTripDate =
                    tripScheduleRepository.findByDestinationAndTripDate(destination, tripDate);

            if (byDestinationAndTripDate.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(byDestinationAndTripDate);
        }

        List<TripScheduleEntity> all = tripScheduleRepository.findAll();
        if (all.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(all);
    }

    @Override
    public ResponseEntity<TripScheduleEntity> getTripDetailsByTripId(long tripId) {
        Optional<TripScheduleEntity> byId = tripScheduleRepository.findById(tripId);
        if(byId.isEmpty())
        {
            return ResponseEntity.noContent().build();
        }
        TripScheduleEntity tripScheduleEntity = byId.get();
        return ResponseEntity.ok(tripScheduleEntity);
    }
}
