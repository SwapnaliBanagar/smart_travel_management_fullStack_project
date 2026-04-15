package com.smartTravel.serviceImpl;

import com.smartTravel.dto.TravelDto;
import com.smartTravel.entity.OwnerEntity;
import com.smartTravel.entity.TravelEntity;
import com.smartTravel.entity.TripScheduleEntity;
import com.smartTravel.repository.OwnerRepository;
import com.smartTravel.repository.TravelRepository;
import com.smartTravel.repository.TripScheduleRepository;
import com.smartTravel.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TravelImpl implements TravelService {
    @Autowired
    OwnerRepository ownerRepository;
    @Autowired
    TravelRepository travelRepository;
    @Autowired
    TripScheduleRepository tripScheduleRepository;

    @Override
    public ResponseEntity<String> addTravelDetails(TravelDto travelDto) {
        Optional<TravelEntity> byTravelNumber = travelRepository.findByTravelNumber(travelDto.getTravelNumber());

        if (byTravelNumber.isPresent()) {
            return ResponseEntity.badRequest().body("This travel details already registered..");
        }

        Optional<OwnerEntity> byId = ownerRepository.findById(travelDto.getOwnerId());
        if (byId.isPresent()) {
            TravelEntity travelEntity = new TravelEntity();
            travelEntity.setTravelName(travelDto.getTravelName());
            travelEntity.setBusType(travelDto.getBusType());
            travelEntity.setSeatType(travelDto.getSeatType());
            travelEntity.setSeaterLowerSeats(travelDto.getSeaterLowerSeats());
            travelEntity.setSleeperLowerSeats(travelDto.getSleeperLowerSeats());
            travelEntity.setSeaterUpperSeats(travelDto.getSeaterUpperSeats());
            travelEntity.setSleeperUpperSeats(travelDto.getSleeperUpperSeats());
            // calculate total seats
            int totalSeats =
                    travelDto.getSeaterLowerSeats()
                            + travelDto.getSeaterUpperSeats()
                            + travelDto.getSleeperLowerSeats()
                            + travelDto.getSleeperUpperSeats();
            travelEntity.setTotalSeats(totalSeats);
            travelEntity.setTravelNumber(travelDto.getTravelNumber());
            OwnerEntity owner = byId.get();
            travelEntity.setOwner(owner);
            travelRepository.save(travelEntity);
            return ResponseEntity.ok("Travel details added successfully..");
        }
        return ResponseEntity.badRequest().body("Owner not found");
    }

    @Override
    public ResponseEntity<TravelEntity> getTravelById(String travelNumber) {
        Optional<TravelEntity> byTravelNumber = travelRepository.findByTravelNumber(travelNumber);
        return byTravelNumber.map(ResponseEntity::ok).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Override
    public ResponseEntity<List<TravelEntity>> getAllTravelDetails() {
        List<TravelEntity> allTravelsList = travelRepository.findAll();
                return ResponseEntity.ok(allTravelsList);
    }

    @Override
    public ResponseEntity<TravelEntity> getTravelDetailsByTripId(long tripId) {
        Optional<TripScheduleEntity> byId = tripScheduleRepository.findById(tripId);
        if(byId.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        TripScheduleEntity tripScheduleEntity = byId.get();
        TravelEntity travel = tripScheduleEntity.getTravel();

        return ResponseEntity.ok(travel);
    }

    @Override
    public ResponseEntity<String> updateById(String travelNumber, TravelDto travelDto) {
        Optional<TravelEntity> byTravelNumber = travelRepository.findByTravelNumber(travelNumber);
        if (byTravelNumber.isPresent()) {
            TravelEntity travelEntity = byTravelNumber.get();
            travelEntity.setTravelName(travelDto.getTravelName());

            if (!travelNumber.equals(travelDto.getTravelNumber())) {
                Optional<TravelEntity> existingTravel =
                        travelRepository.findByTravelNumber(travelDto.getTravelNumber());

                if (existingTravel.isPresent()) {
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body("This travel number is already registered.");
                }
            }
            travelEntity.setTravelNumber(travelDto.getTravelNumber());
            travelEntity.setBusType(travelDto.getBusType());
            travelEntity.setSeatType(travelDto.getSeatType());
            travelEntity.setSeaterLowerSeats(travelDto.getSeaterLowerSeats());
            travelEntity.setSleeperLowerSeats(travelDto.getSleeperLowerSeats());
            travelEntity.setSeaterUpperSeats(travelDto.getSeaterUpperSeats());
            travelEntity.setSleeperUpperSeats(travelDto.getSleeperUpperSeats());
            // calculate total seats
            int totalSeats =
                    travelDto.getSeaterLowerSeats()
                            + travelDto.getSeaterUpperSeats()
                            + travelDto.getSleeperLowerSeats()
                            + travelDto.getSleeperUpperSeats();
            travelEntity.setTotalSeats(totalSeats);
            Optional<OwnerEntity> byId = ownerRepository.findById(travelDto.getOwnerId());
            if (byId.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Owner_id not found.");
            }
            OwnerEntity ownerEntity = byId.get();
            travelEntity.setOwner(ownerEntity);
            travelRepository.save(travelEntity);
            return ResponseEntity.ok("Travels details updated successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Travel number not found.");
    }

    @Override
    public ResponseEntity<String> updateTravelsDetails(String travelNumber, TravelDto travelDto) {
        Optional<TravelEntity> byTravelNumber = travelRepository.findByTravelNumber(travelNumber);
        if (byTravelNumber.isPresent()) {
            TravelEntity travelEntity = byTravelNumber.get();
            travelEntity.setTravelName(travelDto.getTravelName());
            if (!travelNumber.equals(travelDto.getTravelNumber())) {
                Optional<TravelEntity> byTravelNumber1 = travelRepository.findByTravelNumber(travelDto.getTravelNumber());
                if (byTravelNumber1.isPresent()) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("This travels number already register.");
                }
            }
            travelEntity.setTravelNumber(travelDto.getTravelNumber());
            travelEntity.setBusType(travelDto.getBusType());
            travelEntity.setSeatType(travelDto.getSeatType());
            travelEntity.setSeaterLowerSeats(travelDto.getSeaterLowerSeats());
            travelEntity.setSleeperLowerSeats(travelDto.getSleeperLowerSeats());
            travelEntity.setSeaterUpperSeats(travelDto.getSeaterUpperSeats());
            travelEntity.setSleeperUpperSeats(travelDto.getSleeperUpperSeats());
            // calculate total seats
            int totalSeats =
                    travelDto.getSeaterLowerSeats()
                            + travelDto.getSeaterUpperSeats()
                            + travelDto.getSleeperLowerSeats()
                            + travelDto.getSleeperUpperSeats();
            travelEntity.setTotalSeats(totalSeats);
            Optional<OwnerEntity> byId = ownerRepository.findById(travelDto.getOwnerId());
            if (byId.isPresent()) {
                OwnerEntity ownerEntity = byId.get();
                travelEntity.setOwner(ownerEntity);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Please enter correct owner_id.");
            }
            travelRepository.save(travelEntity);
            return ResponseEntity.ok("Travel details updated successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Please enter correct travel number.");
    }

    @Override
    public ResponseEntity<String> deleteTravelDetails(String travelNumber) {
        Optional<TravelEntity> byTravelNumber = travelRepository.findByTravelNumber(travelNumber);
        if (byTravelNumber.isPresent()) {
            travelRepository.delete(byTravelNumber.get());
            return ResponseEntity.ok("Travel details deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This travel number is not found");
    }


}
