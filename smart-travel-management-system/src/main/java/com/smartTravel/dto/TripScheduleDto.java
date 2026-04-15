package com.smartTravel.dto;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class TripScheduleDto {
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate tripDate;
    private String source;
    private String destination;
    private double seaterSeatPrice;
    private double sleeperSeatPrice;
    private int totalBookedSeats;

    // stops with time
    private Map<String, String> stops = new LinkedHashMap<>();
    // travel reference
    private String travelNumber;


    public TripScheduleDto() {
    }

    public TripScheduleDto(LocalDate tripDate, String source, String destination, double seaterSeatPrice, double sleeperSeatPrice, int totalBookedSeats, Map<String, String> stops, String travelNumber) {
        this.tripDate = tripDate;
        this.source = source;
        this.destination = destination;
        this.seaterSeatPrice = seaterSeatPrice;
        this.sleeperSeatPrice = sleeperSeatPrice;
        this.totalBookedSeats = totalBookedSeats;
        this.stops = stops;
        this.travelNumber = travelNumber;
    }

    public LocalDate getTripDate() {
        return tripDate;
    }

    public void setTripDate(LocalDate tripDate) {
        this.tripDate = tripDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getSeaterSeatPrice() {
        return seaterSeatPrice;
    }

    public void setSeaterSeatPrice(double seaterSeatPrice) {
        this.seaterSeatPrice = seaterSeatPrice;
    }

    public double getSleeperSeatPrice() {
        return sleeperSeatPrice;
    }

    public void setSleeperSeatPrice(double sleeperSeatPrice) {
        this.sleeperSeatPrice = sleeperSeatPrice;
    }

    public int getTotalBookedSeats() {
        return totalBookedSeats;
    }

    public void setTotalBookedSeats(int totalBookedSeats) {
        this.totalBookedSeats = totalBookedSeats;
    }

    public Map<String, String> getStops() {
        return stops;
    }

    public void setStops(Map<String, String> stops) {
        this.stops = stops;
    }

    public String getTravelNumber() {
        return travelNumber;
    }

    public void setTravelNumber(String travelNumber) {
        this.travelNumber = travelNumber;
    }
}
