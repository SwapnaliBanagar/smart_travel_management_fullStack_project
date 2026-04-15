package com.smartTravel.dto;

import java.util.List;

public class PaypalDto {
    private double amount;
    private List<Long> seatIds;

    public PaypalDto() {
    }

    public PaypalDto(double amount, List<Long> seatIds) {
        this.amount = amount;
        this.seatIds = seatIds;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public List<Long> getSeatIds() {
        return seatIds;
    }

    public void setSeatIds(List<Long> seatIds) {
        this.seatIds = seatIds;
    }
}
