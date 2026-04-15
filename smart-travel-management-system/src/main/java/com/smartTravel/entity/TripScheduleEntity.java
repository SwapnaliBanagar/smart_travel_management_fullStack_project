package com.smartTravel.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "trip_schedule_datils")
public class TripScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tripId;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate tripDate;
    private String source;
    private String destination;
    private double seaterSeatPrice;
    private double sleeperSeatPrice;
    private int totalBookedSeats;

    // ✅ Correct way to store stops
    @ElementCollection
    @CollectionTable(name = "trip_stops",
            joinColumns = @JoinColumn(name = "trip_id"))
    @MapKeyColumn(name = "stop_name")
    @Column(name = "stop_time")
    private Map<String, String> stops = new LinkedHashMap<>();

    @ManyToOne
    @JoinColumn(name = "travel_id", nullable = false)
    private TravelEntity travel;

    @OneToMany(mappedBy = "tripSchedule", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<SeatEntity> seats = new ArrayList<>();

    public TripScheduleEntity() {
    }

    public TripScheduleEntity(Long tripId, LocalDate tripDate, String source, String destination, double seaterSeatPrice, double sleeperSeatPrice, int totalBookedSeats, Map<String, String> stops, TravelEntity travel, List<SeatEntity> seats) {
        this.tripId = tripId;
        this.tripDate = tripDate;
        this.source = source;
        this.destination = destination;
        this.seaterSeatPrice = seaterSeatPrice;
        this.sleeperSeatPrice = sleeperSeatPrice;
        this.totalBookedSeats = totalBookedSeats;
        this.stops = stops;
        this.travel = travel;
        this.seats = seats;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
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

    public TravelEntity getTravel() {
        return travel;
    }

    public void setTravel(TravelEntity travel) {
        this.travel = travel;
    }

    public List<SeatEntity> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatEntity> seats) {
        this.seats = seats;
    }
}