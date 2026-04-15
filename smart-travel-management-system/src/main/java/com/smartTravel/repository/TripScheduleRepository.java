package com.smartTravel.repository;

import com.smartTravel.entity.TripScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface TripScheduleRepository extends JpaRepository<TripScheduleEntity, Long> {
    List<TripScheduleEntity>findBySourceAndDestinationAndTripDate(String source, String destination, LocalDate tripDate);
    List<TripScheduleEntity> findBySource(String source);
    List<TripScheduleEntity> findByDestination(String destination);
    List<TripScheduleEntity>findByTripDate(LocalDate tripDate);
    List<TripScheduleEntity> findBySourceAndDestination(String source, String destination);
    List<TripScheduleEntity> findBySourceAndTripDate(String source,LocalDate tripDate);
    List<TripScheduleEntity> findByDestinationAndTripDate(String destination, LocalDate tripDate);
}
