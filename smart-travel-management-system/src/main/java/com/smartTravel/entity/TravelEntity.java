package com.smartTravel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "travel_details")
public class TravelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long travelId;
    private String travelName;
    private String travelNumber;
    private String busType;
    private String seatType;
    // seat counts
    private int sleeperUpperSeats;
    private int sleeperLowerSeats;

    private int seaterUpperSeats;
    private int seaterLowerSeats;

    private int totalSeats;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private OwnerEntity owner;

    @OneToMany(mappedBy = "travel", cascade = CascadeType.ALL)
    @JsonIgnore
    List<TripScheduleEntity> trip;

    public TravelEntity() {
    }

    public TravelEntity(Long travelId, String travelName, String travelNumber, String busType, String seatType, int sleeperUpperSeats, int sleeperLowerSeats, int seaterUpperSeats, int seaterLowerSeats, int totalSeats, OwnerEntity owner, List<TripScheduleEntity> trip) {
        this.travelId = travelId;
        this.travelName = travelName;
        this.travelNumber = travelNumber;
        this.busType = busType;
        this.seatType = seatType;
        this.sleeperUpperSeats = sleeperUpperSeats;
        this.sleeperLowerSeats = sleeperLowerSeats;
        this.seaterUpperSeats = seaterUpperSeats;
        this.seaterLowerSeats = seaterLowerSeats;
        this.totalSeats = totalSeats;
        this.owner = owner;
        this.trip = trip;
    }

    public Long getTravelId() {
        return travelId;
    }

    public void setTravelId(Long travelId) {
        this.travelId = travelId;
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

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public OwnerEntity getOwner() {
        return owner;
    }

    public void setOwner(OwnerEntity owner) {
        this.owner = owner;
    }

    public List<TripScheduleEntity> getTrip() {
        return trip;
    }

    public void setTrip(List<TripScheduleEntity> trip) {
        this.trip = trip;
    }
}