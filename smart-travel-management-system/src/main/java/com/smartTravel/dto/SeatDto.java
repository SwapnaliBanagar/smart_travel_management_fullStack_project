package com.smartTravel.dto;

public class SeatDto {
  
    private String seatNumber;
    private String status;
    private Long tripId;

    public SeatDto() {
    }

    public SeatDto(String seatNumber, String status, Long tripId) {
        this.seatNumber = seatNumber;
        this.status = status;
        this.tripId = tripId;
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

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }
}
