package com.smartTravel.repository;

import com.smartTravel.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SeatRepository extends JpaRepository<SeatEntity,Long> {
    List<SeatEntity> findByTripSchedule_TripId(Long tripId);
}
