package com.smartTravel.dto;

public class TravelDto {
    private String travelName;
    private String travelNumber;
    private String busType;
    private String seatType;
    private int sleeperUpperSeats;
    private int sleeperLowerSeats;
    private int seaterUpperSeats;
    private int seaterLowerSeats;
    private Long ownerId;

    public TravelDto() {
    }

    public TravelDto(String travelName, String travelNumber, String busType, String seatType, int sleeperUpperSeats, int sleeperLowerSeats, int seaterUpperSeats, int seaterLowerSeats,  Long ownerId) {
        this.travelName = travelName;
        this.travelNumber = travelNumber;
        this.busType = busType;
        this.seatType = seatType;
        this.sleeperUpperSeats = sleeperUpperSeats;
        this.sleeperLowerSeats = sleeperLowerSeats;
        this.seaterUpperSeats = seaterUpperSeats;
        this.seaterLowerSeats = seaterLowerSeats;
        this.ownerId = ownerId;
    }

    public String getTravelName() {
        return travelName;
    }

    public void setTravelName(String travelName) {
        this.travelName = travelName;
    }

    public String getTravelNumber() {
        return travelNumber;
    }

    public void setTravelNumber(String travelNumber) {
        this.travelNumber = travelNumber;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public int getSleeperUpperSeats() {
        return sleeperUpperSeats;
    }

    public void setSleeperUpperSeats(int sleeperUpperSeats) {
        this.sleeperUpperSeats = sleeperUpperSeats;
    }

    public int getSleeperLowerSeats() {
        return sleeperLowerSeats;
    }

    public void setSleeperLowerSeats(int sleeperLowerSeats) {
        this.sleeperLowerSeats = sleeperLowerSeats;
    }

    public int getSeaterUpperSeats() {
        return seaterUpperSeats;
    }

    public void setSeaterUpperSeats(int seaterUpperSeats) {
        this.seaterUpperSeats = seaterUpperSeats;
    }

    public int getSeaterLowerSeats() {
        return seaterLowerSeats;
    }

    public void setSeaterLowerSeats(int seaterLowerSeats) {
        this.seaterLowerSeats = seaterLowerSeats;
    }
    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
