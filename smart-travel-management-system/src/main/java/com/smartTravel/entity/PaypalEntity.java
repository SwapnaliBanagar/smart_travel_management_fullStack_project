package com.smartTravel.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "payment_details")
public class PaypalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Internal Primary Key

    @Column(nullable = false, unique = true)
    private String orderId; // PayPal Order ID

    private String paymentId; // Capture ID (after successful payment)

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String status; // CREATED / SUCCESS / FAILED

    private String currency; // Optional (INR, USD)

    private String paymentMethod; // PAYPAL

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // 🔗 Relation with Seat
    @ManyToMany
    @JoinTable(
            name = "payment_seats",
            joinColumns = @JoinColumn(name = "payment_id"),
            inverseJoinColumns = @JoinColumn(name = "seat_id")
    )
    private List<SeatEntity> seats;

    public PaypalEntity() {
    }

    public PaypalEntity(String orderId, Double amount, String currency, List<SeatEntity> seats) {
        this.orderId = orderId;
        this.amount = amount;
        this.currency = currency;
        this.seats = seats;
    }

    // ✅ Auto set on create
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.status = "CREATED";
        this.paymentMethod = "PAYPAL";
    }

    // ✅ Auto update
    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<SeatEntity> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatEntity> seats) {
        this.seats = seats;
    }
}
