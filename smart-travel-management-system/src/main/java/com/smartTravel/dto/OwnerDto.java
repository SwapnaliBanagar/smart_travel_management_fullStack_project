package com.smartTravel.dto;

import jakarta.persistence.Column;

public class OwnerDto {

    private String userName;
    private String password;
    private String email;
    private String mobileNumber;

    public OwnerDto() {
    }

    public OwnerDto(String userName, String password, String email, String mobileNumber) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.mobileNumber = mobileNumber;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
