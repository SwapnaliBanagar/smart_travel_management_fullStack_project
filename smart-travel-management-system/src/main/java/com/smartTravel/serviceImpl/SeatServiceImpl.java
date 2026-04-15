package com.smartTravel.serviceImpl;

import com.smartTravel.entity.SeatEntity;
import com.smartTravel.repository.SeatRepository;
import com.smartTravel.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {
    @Autowired
    private SeatRepository seatRepository;

    @Override
    public ResponseEntity<List<SeatEntity>> getSeats(Long tripId) {

        List<SeatEntity> seats =
                seatRepository.findByTripSchedule_TripId(tripId);

        if(seats.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(seats);
    }
}
