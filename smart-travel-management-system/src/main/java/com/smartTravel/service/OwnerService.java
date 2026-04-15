package com.smartTravel.service;

import com.smartTravel.dto.OwnerDto;
import com.smartTravel.entity.OwnerEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public interface OwnerService {
    public ResponseEntity<String> addOwnerDetails(OwnerDto ownerDto);
    public ResponseEntity<OwnerEntity> getOwnerDetailsById(Long ownerId);
    public ResponseEntity<List<OwnerEntity>>getAllOwnerDetails();
    public ResponseEntity<String>updateOwerDetails(OwnerDto ownerDto,Long ownerId);
    public ResponseEntity<String>updateDetails(OwnerDto ownerDto,Long ownerId);
    public ResponseEntity<String>deleteOwnerDetails(long ownerId);
}
