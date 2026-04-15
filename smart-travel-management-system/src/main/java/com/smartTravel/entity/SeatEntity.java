package com.smartTravel.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "seat_details")
public class SeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;
    private String seatNumber;      // L1, U26, etc.
    private String status;          // AVAILABLE / BOOKED / SELECTED / SPECIAL
    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    @JsonBackReference
    private TripScheduleEntity tripSchedule;

    public SeatEntity() {
    }

    public SeatEntity(Long seatId, String seatNumber, String status, TripScheduleEntity tripSchedule) {
        this.seatId = seatId;
        this.seatNumber = seatNumber;
        this.status = status;
        this.tripSchedule = tripSchedule;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TripScheduleEntity getTripSchedule() {
        return tripSchedule;
    }

    public void setTripSchedule(TripScheduleEntity tripSchedule) {
        this.tripSchedule = tripSchedule;
    }
}


