package com.smartTravel.controller;

import com.smartTravel.dto.OwnerDto;
import com.smartTravel.entity.OwnerEntity;
import com.smartTravel.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owner")
@CrossOrigin(origins = "http://localhost:4200")
public class OwnerController {
    @Autowired
    OwnerService ownerService;

    @PostMapping("/registration")
    public ResponseEntity<String> addOwnerDetails(@RequestBody OwnerDto ownerDto) {
        return ownerService.addOwnerDetails(ownerDto);
    }

    @GetMapping("/getOwnerDetails/{ownerId}")
    public ResponseEntity<OwnerEntity> getOwnerDetailsById(@PathVariable Long ownerId) {
        return ownerService.getOwnerDetailsById(ownerId);
    }

    @GetMapping("/getAllOwnerDetails")
    public ResponseEntity<List<OwnerEntity>> getAllOwnerDetails() {
        return ownerService.getAllOwnerDetails();
    }


    @PutMapping("/update/{ownerId}")
    public ResponseEntity<String> updateOwerDetails(@RequestBody OwnerDto ownerDto, @PathVariable Long ownerId) {
        return ownerService.updateOwerDetails(ownerDto, ownerId);
    }

    @PatchMapping("/updateDetails/{ownerId}")
    public ResponseEntity<String> updateDetails(@RequestBody OwnerDto ownerDto, @PathVariable Long ownerId) {
        return ownerService.updateDetails(ownerDto, ownerId);
    }


    @DeleteMapping("/delete/{ownerId}")
    public ResponseEntity<String> deleteOwnerDetails(@PathVariable long ownerId) {
        return ownerService.deleteOwnerDetails(ownerId);
    }

}

