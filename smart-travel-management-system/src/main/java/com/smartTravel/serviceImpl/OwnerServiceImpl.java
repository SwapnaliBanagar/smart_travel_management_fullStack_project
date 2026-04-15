package com.smartTravel.serviceImpl;

import com.smartTravel.dto.OwnerDto;
import com.smartTravel.entity.OwnerEntity;
import com.smartTravel.entity.TravelEntity;
import com.smartTravel.repository.OwnerRepository;
import com.smartTravel.repository.TravelRepository;
import com.smartTravel.service.OwnerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerServiceImpl implements OwnerService {
    @Autowired
    OwnerRepository ownerRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    TravelRepository travelRepository;


    @Override
    public ResponseEntity<String> addOwnerDetails(OwnerDto ownerDto) {
        Optional<OwnerEntity> byUserName = ownerRepository.findByUserName(ownerDto.getUserName());
        if (byUserName.isPresent()) {
            return ResponseEntity.badRequest().body("Please enter unique username.");
        }
        Optional<OwnerEntity> byEmail = ownerRepository.findByEmail(ownerDto.getEmail());
        if (byEmail.isPresent()) {
            return ResponseEntity.badRequest().body("Please enter unique email.");
        }


        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.setUserName(ownerDto.getUserName());
        ownerEntity.setPassword(passwordEncoder.encode(ownerDto.getPassword()));
        ownerEntity.setEmail(ownerDto.getEmail());
        ownerEntity.setMobileNumber(ownerDto.getMobileNumber());
        ownerRepository.save(ownerEntity);
        return ResponseEntity.ok("Owner details added successfully.." + "\n" + "Your owner_id is:" + ownerEntity.getOwnerId());
    }

    @Override
    public ResponseEntity<OwnerEntity> getOwnerDetailsById(Long ownerId) {
        Optional<OwnerEntity> byId = ownerRepository.findById(ownerId);
        return byId.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Override
    public ResponseEntity<List<OwnerEntity>> getAllOwnerDetails() {
        List<OwnerEntity> allOwnerList = ownerRepository.findAll();
        if (allOwnerList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(allOwnerList);
    }


    @Override
    public ResponseEntity<String> updateOwerDetails(OwnerDto ownerDto, Long ownerId) {
        Optional<OwnerEntity> byId = ownerRepository.findById(ownerId);
        if (byId.isPresent()) {
            OwnerEntity ownerEntity = byId.get();
            ownerEntity.setOwnerId(ownerId);
            Optional<OwnerEntity> byUserName = ownerRepository.findByUserName(ownerDto.getUserName());
            if (byUserName.isPresent() && !byUserName.get().getOwnerId().equals(ownerId)) {
                return ResponseEntity.badRequest().body("Please enter unique username.");
            }
            ownerEntity.setUserName(ownerDto.getUserName());
            ownerEntity.setPassword(ownerDto.getPassword());
            Optional<OwnerEntity> byEmail = ownerRepository.findByEmail(ownerDto.getEmail());
            if (byEmail.isPresent() && !byEmail.get().getOwnerId().equals(ownerId)) {
                return ResponseEntity.badRequest().body("Please enter unique email.");
            }
            ownerEntity.setEmail(ownerDto.getEmail());
            ownerEntity.setMobileNumber(ownerDto.getMobileNumber());
            ownerRepository.save(ownerEntity);
            return ResponseEntity.ok("Owner details updated successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Please enter correct owner_id.");
    }


    @Override
    public ResponseEntity<String> updateDetails(OwnerDto ownerDto, Long ownerId) {
        Optional<OwnerEntity> byId = ownerRepository.findById(ownerId);
        if (byId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Please enter correct owner_id.");
        }
        OwnerEntity ownerEntity = byId.get();
        if (ownerDto.getUserName() != null) {
            Optional<OwnerEntity> byUserName = ownerRepository.findByUserName(ownerDto.getUserName());

            if (byUserName.isPresent() && !byUserName.get().getOwnerId().equals(ownerId)) {
                return ResponseEntity.badRequest().body("Please enter unique username.");
            }
            ownerEntity.setUserName(ownerDto.getUserName());
        }
        if (ownerDto.getPassword() != null) {
            ownerEntity.setPassword(ownerDto.getPassword());
        }

        if (ownerDto.getEmail() != null) {
            Optional<OwnerEntity> byEmail = ownerRepository.findByEmail(ownerDto.getEmail());
            if (byEmail.isPresent() && !byEmail.get().getOwnerId().equals(ownerId)) {
                return ResponseEntity.badRequest().body("Please enter unique email.");
            }
            ownerEntity.setEmail(ownerDto.getEmail());
        }
        if (ownerDto.getMobileNumber() != null) {
            ownerEntity.setMobileNumber(ownerDto.getMobileNumber());
        }
        ownerRepository.save(ownerEntity);
        return ResponseEntity.ok("Owner details updated successfully..");
    }




    @Override
    public ResponseEntity<String> deleteOwnerDetails(long ownerId) {
        Optional<OwnerEntity> byId = ownerRepository.findById(ownerId);
        if (byId.isPresent()) {
            ownerRepository.deleteById(ownerId);
            return ResponseEntity.ok("Owner details deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Owner_id not found..Please enter correct owner_id");
    }


}
