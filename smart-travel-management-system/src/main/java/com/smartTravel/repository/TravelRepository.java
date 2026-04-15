package com.smartTravel.repository;

import com.smartTravel.entity.OwnerEntity;
import com.smartTravel.entity.TravelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TravelRepository extends JpaRepository<TravelEntity,Long> {

    Optional<TravelEntity> findByTravelNumber(String travelNumber);


}
